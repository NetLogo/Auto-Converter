package org.nlogo.convert

import java.awt.EventQueue
import java.util.{ List => JList, Map => JMap }

import org.nlogo.agent.World
import org.nlogo.api.{ DummyLogoThunkFactory, FileIO, ModelReader, ModelSection, Shape, ShapeList, Version,
                       VersionHistory }
import org.nlogo.app.InfoConverter
import org.nlogo.compiler.Compiler
import org.nlogo.editor.AbstractEditorArea
import org.nlogo.lab.{ Protocol, ProtocolLoader, ProtocolSaver }
import org.nlogo.nvm.DefaultCompilerServices
import org.nlogo.plot.PlotManager
import org.nlogo.shape.{ LinkShape, VectorShape }
import org.nlogo.swing.Implicits.thunk2runnable
import org.nlogo.util.MersenneTwisterFast
import org.nlogo.window.{ ButtonWidget, ChooserWidget, DummyButtonWidget, DummyChooserWidget, DummyInputBoxWidget,
                          DummyMonitorWidget, DummyPlotWidget, DummySliderWidget, DummyViewWidget, InputBoxWidget,
                          MonitorWidget, OutputWidget, PlotWidget, SliderWidget, Widget, WidgetRegistry }

object AutoConverter {
  def main(args: Array[String]): Unit = {
    if (args.isEmpty)
      System.exit(1)

    val modelPath: String = args(0)

    val source: String = FileIO.file2String(modelPath)

    val sections: JMap[ModelSection, Array[String]] = ModelReader.parseModel(source)

    val buffer = new StringBuilder

    def getSectionLines(section: ModelSection, default: Array[String] = Array()): Array[String] =
      Option(sections.get(section)).filter(_.nonEmpty).getOrElse(default)

    def getSectionText(section: ModelSection, default: String = ""): String =
      Option(sections.get(section)).filter(_.nonEmpty).map(_.mkString("\n")).getOrElse(default)

    def ensureNewline(): Unit = {
      if (buffer.nonEmpty && buffer.last != '\n')
        buffer += '\n'
    }

    def writeSection(f: => Unit): Unit = {
      f

      ensureNewline()

      buffer ++= ModelReader.SEPARATOR + '\n'
    }

    val modelVersion: String = getSectionText(ModelSection.Version, "5.0.0")

    def autoConvert(text: String, subprogram: Boolean, reporter: Boolean): String =
      Compiler.autoConvert(text, subprogram, reporter, modelVersion, null, false, false)

    val compilerServices = new DefaultCompilerServices(Compiler)

    val plotManager = new PlotManager(new DummyLogoThunkFactory)

    val editorArea = new AbstractEditorArea {
      override def enableBracketMatcher(enable: Boolean): Unit = {}
    }

    writeSection {
      buffer ++= autoConvert(getSectionText(ModelSection.Code), false, false)
    }

    writeSection {
      val widgetLines: JList[JList[String]] = ModelReader.parseWidgets(getSectionLines(ModelSection.Interface))

      for (i <- 0 until widgetLines.size) {
        val lines: JList[String] = widgetLines.get(i)

        val tpe: String = lines.get(0).toUpperCase

        Option(WidgetRegistry(tpe)).orElse {
          tpe match {
            case "SLIDER" =>
              Some(new SliderWidget(null))

            case "CHOOSER" | "CHOICE" =>
              Some(new ChooserWidget(compilerServices))

            case "BUTTON" =>
              Some(new ButtonWidget(null))

            case "PLOT" =>
              Some(PlotWidget(plotManager))

            case "MONITOR" =>
              Some(new MonitorWidget(null))

            case "INPUT" | "INPUTBOX" =>
              Some(new InputBoxWidget(editorArea, editorArea, compilerServices, editorArea))

            case "OUTPUT" =>
              Some(new OutputWidget)

            case "GRAPHICS-WINDOW" =>
              Some(new IdentityWidget)

            case "CC-WINDOW" =>
              None

            case _ =>
              throw new IllegalStateException("Unknown widget type \"" + tpe + "\".")
          }
        }.foreach { widget =>
          val loadHelper = new Widget.LoadHelper {
            override def convert(source: String, reporter: Boolean): String =
              autoConvert(source, true, reporter)

            override def version: String =
              modelVersion
          }

          EventQueue.invokeAndWait(() => {
            widget.load(lines.toArray(Array[String]()), loadHelper)
            widget.setLocation(lines.get(1).toInt, lines.get(2).toInt)
          }: Unit)

          buffer ++= widget.save + '\n'
        }
      }
    }

    writeSection {
      val text: String = getSectionText(ModelSection.Info)

      if (VersionHistory.olderThan42pre2(modelVersion)) {
        buffer ++= InfoConverter.convert(getSectionText(ModelSection.Info))
      } else {
        buffer ++= getSectionText(ModelSection.Info)
      }
    }

    writeSection {
      val lines: Array[String] = getSectionLines(ModelSection.TurtleShapes, ModelReader.defaultShapes)

      val parsedShapes: JList[Shape] = VectorShape.parseShapes(lines, modelVersion)
      val transformedShapes: JList[Shape] = new ShapeList(parsedShapes.toArray(Array[Shape]()): _*).getShapes

      for (i <- 0 until transformedShapes.size)
        buffer ++= transformedShapes.get(i).toString + "\n\n"
    }

    writeSection {
      buffer ++= Version.version + '\n'
    }

    writeSection {
      buffer ++= getSectionText(ModelSection.PreviewCommands).trim + '\n'
    }

    writeSection {
      val text: String = getSectionText(ModelSection.SystemDynamics).trim

      buffer ++= text.replaceAll("org.nlogo.aggregate.gui", "org.nlogo.sdm.gui") + '\n'
    }

    writeSection {
      val text: String = getSectionText(ModelSection.BehaviorSpace)

      if (text.trim.nonEmpty) {
        val protocols: List[Protocol] = new ProtocolLoader(compilerServices).loadAll(text)

        if (protocols.nonEmpty) {
          buffer ++= ProtocolSaver.save(protocols.map { protocol =>
            new Protocol(
              protocol.name,
              autoConvert(protocol.setupCommands, true, false),
              autoConvert(protocol.goCommands, true, false),
              autoConvert(protocol.finalCommands, true, false),
              protocol.repetitions,
              protocol.runMetricsEveryStep,
              protocol.timeLimit,
              autoConvert(protocol.exitCondition, true, true),
              protocol.metrics.map(autoConvert(_, true, true)),
              protocol.valueSets
            )
          })
        }
      }
    }

    writeSection {
      val widgetLines: JList[JList[String]] = ModelReader.parseWidgets(getSectionLines(ModelSection.HubNetClient))

      for (i <- 0 until widgetLines.size) {
        val lines: JList[String] = widgetLines.get(i)

        val tpe: String = lines.get(0).toUpperCase

        Option(WidgetRegistry("DUMMY " + tpe)).orElse {
          tpe match {
            case "SLIDER" =>
              Some(new DummySliderWidget())

            case "CHOOSER" | "CHOICE" =>
              Some(new DummyChooserWidget(compilerServices))

            case "BUTTON" =>
              Some(new DummyButtonWidget())

            case "PLOT" =>
              Some(DummyPlotWidget(plotManager.getPlotNames.headOption.getOrElse("plot 1"), plotManager))

            case "MONITOR" =>
              Some(new DummyMonitorWidget())

            case "INPUT" | "INPUTBOX" =>
              Some(new DummyInputBoxWidget(editorArea, editorArea, editorArea, compilerServices))

            case "OUTPUT" =>
              Some(new OutputWidget)

            case "GRAPHICS-WINDOW" | "VIEW" =>
              Some(new DummyViewWidget(new World))

            case "CC-WINDOW" =>
              None

            case _ =>
              throw new IllegalStateException("Unknown widget type \"" + tpe + "\".")
          }
        }.foreach { widget =>
          val loadHelper = new Widget.LoadHelper {
            override def convert(source: String, reporter: Boolean): String =
              autoConvert(source, true, reporter)

            override def version: String =
              modelVersion
          }

          EventQueue.invokeAndWait(() => {
            widget.load(lines.toArray(Array[String]()), loadHelper)
            widget.setLocation(lines.get(1).toInt, lines.get(2).toInt)
          }: Unit)

          buffer ++= widget.save + '\n'
        }
      }
    }

    writeSection {
      val lines: Array[String] = getSectionLines(ModelSection.LinkShapes, ModelReader.defaultLinkShapes)

      val parsedShapes: JList[Shape] = LinkShape.parseShapes(lines, modelVersion)
      val transformedShapes: JList[Shape] = new ShapeList(parsedShapes.toArray(Array[Shape]()): _*).getShapes

      for (i <- 0 until transformedShapes.size)
        buffer ++= transformedShapes.get(i).toString + "\n\n"
    }

    writeSection {
      if (getSectionLines(ModelSection.ModelSettings).headOption.filter(_.trim.nonEmpty).exists(_.trim != "0")) {
        buffer ++= "1\n"
      } else {
        buffer ++= "0\n"
      }
    }

    FileIO.writeFile(modelPath, buffer.toString)

    println(modelPath)
  }

  private class IdentityWidget extends Widget {
    private var lines = Array[String]()

    override def load(lines: Array[String], helper: Widget.LoadHelper): AnyRef = {
      this.lines = lines

      this
    }

    override def save: String =
      lines.mkString("\n") + '\n'
  }
}
