package org.nlogo.convert

import java.net.URI
import java.nio.file.Paths

import org.nlogo.api.{ ModelType, NetLogoLegacyDialect, NetLogoThreeDDialect, Version }
import org.nlogo.core.{ Femto, LiteralParser, Model }
import org.nlogo.fileformat.{ FailedConversionResult, FileFormat }
import org.nlogo.headless.HeadlessWorkspace
import org.nlogo.sdm.SDMAutoConvertable
import org.nlogo.workspace.{ OpenModel, OpenModelFromURI, SaveModel }

import scala.util.Failure

object AutoConverter {
  def main(args: Array[String]): Unit = {
    if (args.isEmpty)
      System.exit(1)

    val modelPath = Paths.get(args(0))
    val savePath = modelPath.toString.replaceAll("\\.nlogo$", ".nlogox")

    val literalParser = Femto.scalaSingleton[LiteralParser]("org.nlogo.parse.CompilerUtilities")

    val workspace = HeadlessWorkspace.newInstance

    try {
      val converter = FileFormat.converter(workspace.getExtensionManager, workspace.getLibraryManager,
                                           workspace.getCompilationEnvironment, literalParser,
                                           FileFormat.defaultAutoConvertables :+ SDMAutoConvertable)

      val modelLoader = FileFormat.standardAnyLoader(false, workspace.compiler.utilities)

      val controller = new ResaveController(modelPath.toUri)

      val dialect = {
        if (Version.is3D) {
          NetLogoThreeDDialect
        } else {
          NetLogoLegacyDialect
        }
      }

      OpenModelFromURI(modelPath.toUri, controller, modelLoader, converter(dialect), Version).foreach { model =>
        SaveModel(model, modelLoader, controller, workspace, Version).map(_.apply()) match {
          case Some(Failure(ex)) =>
            ex.printStackTrace()

            System.exit(1)

          case None =>
            System.exit(1)

          case _ =>
            println(savePath)
        }
      }
    } finally {
      workspace.dispose()
    }
  }

  private class ResaveController(path: URI) extends OpenModel.Controller with SaveModel.Controller {
    override def chooseFilePath(modelType: ModelType): Option[URI] =
      Some(path)

    def warnInvalidFileFormat(format: String): Unit = {
      System.exit(1)
    }

    def errorOpeningURI(uri: URI, ex: Exception): Unit = {
      ex.printStackTrace()

      System.exit(1)
    }

    def invalidModel(uri: URI): Unit = {
      System.exit(1)
    }

    def invalidModelVersion(uri: URI, version: String): Unit = {
      System.exit(1)
    }

    def errorAutoconvertingModel(res: FailedConversionResult): Option[Model] = {
      res.errors.foreach(_.errors.foreach(_.printStackTrace()))

      System.exit(1)

      None
    }

    override def shouldSaveModelOfDifferingVersion(version: String): Boolean = true
    override def shouldOpenModelOfDifferingArity(arity: Int, version: String): Boolean = true
    override def shouldOpenModelOfLegacyVersion(version: String): Boolean = true
    override def shouldOpenModelOfUnknownVersion(version: String): Boolean = true
  }
}
