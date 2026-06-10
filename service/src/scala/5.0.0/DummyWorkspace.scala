package org.nlogo.convert

import java.awt.image.BufferedImage
import java.io.{ PrintWriter, Reader }
import java.lang.{ Boolean => JBoolean }
import java.util.{ HashMap, List => JList, Map => JMap, WeakHashMap }

import org.nlogo.agent.{ Agent, AgentSet, World }
import org.nlogo.api.{ Agent => ApiAgent, AggregateManagerInterface, Approximate, CommandRunnable,
                       DummyExtensionManager, DummyLogoThunkFactory, ExtensionManager, FileIO, HubNetInterface,
                       ImportErrorHandler, JobOwner, LogoException, ModelReader, ModelSection, PlotInterface,
                       ReporterRunnable, Shape, ShapeList, Token, Version, VersionHistory, WorldDimensions,
                       WorldDimensions3D }
import org.nlogo.nvm.{ Activation, Command, CompilerInterface, Context, DefaultCompilerServices, FileManager,
                       Instruction, Job, MutableLong, Procedure, Tracer, Workspace }
import org.nlogo.util.MersenneTwisterFast

private class DummyWorkspace extends Workspace {
  private val w = new World

  private var procedures: JMap[String, Procedure] = new HashMap[String, Procedure]()

  private val extensionManager = new DummyExtensionManager

  override def world: World =
    w

  override def getProcedures: JMap[String, Procedure] =
    procedures

  override def setProcedures(procedures: JMap[String, Procedure]): Unit = {
    this.procedures = procedures
  }

  override def getExtensionManager: ExtensionManager =
    extensionManager

  override def aggregateManager: AggregateManagerInterface = unsupported
  override def requestDisplayUpdate(force: Boolean): Unit = unsupported
  override def breathe(): Unit = unsupported
  override def joinForeverButtons(agent: Agent): Unit = unsupported
  override def addJobFromJobThread(job: Job): Unit = unsupported
  override def waitFor(runnable: CommandRunnable): Unit = unsupported
  override def waitForResult[T](runnable: ReporterRunnable[T]): T = unsupported
  override def importWorld(reader: Reader): Unit = unsupported
  override def importWorld(path: String): Unit = unsupported
  override def importDrawing(path: String): Unit = unsupported
  override def clearDrawing(): Unit = unsupported
  override def exportDrawing(path: String, format: String): Unit = unsupported
  override def exportView(path: String, format: String): Unit = unsupported
  override def exportView: BufferedImage = unsupported
  override def exportInterface(path: String): Unit = unsupported
  override def exportWorld(path: String): Unit = unsupported
  override def exportWorld(writer: PrintWriter): Unit = unsupported
  override def exportOutput(path: String): Unit = unsupported
  override def exportPlot(plotName: String, path: String): Unit = unsupported
  override def exportAllPlots(path: String): Unit = unsupported
  override def inspectAgent(agent: ApiAgent, radius: Double): Unit = unsupported
  override def inspectAgent(agentClass: Class[_ <: Agent], agent: Agent, radius: Double): Unit = unsupported
  override def getAndCreateDrawing: BufferedImage = unsupported
  override def getHubNetManager: HubNetInterface = unsupported
  override def waitForQueuedEvents(): Unit = unsupported
  override def outputObject(obj: AnyRef, owner: AnyRef, addNewline: Boolean, readable: Boolean,
                            destination: Workspace.OutputDestination): Unit = unsupported
  override def clearOutput(): Unit = unsupported
  override def clearAll(): Unit = unsupported
  override def compileForRun(source: String, context: Context, reporter: Boolean): Procedure = unsupported
  override def convertToNormal: String = unsupported
  override def getModelPath: String = unsupported
  override def setModelPath(path: String): Unit = unsupported
  override def getModelDir: String = unsupported
  override def getModelFileName: String = unsupported
  override def fileManager: FileManager = unsupported
  override def plotManager: AnyRef = unsupported
  override def updatePlots(c: Context): Unit = unsupported
  override def setupPlots(c: Context): Unit = unsupported
  override def previewCommands: String = unsupported
  override def tick(c: Context, originalInstruction: Instruction): Unit = unsupported
  override def resetTicks(c: Context): Unit = unsupported
  override def clearTicks(): Unit = unsupported
  override def attachModelDir(filePath: String): String = unsupported
  override def evaluateCommands(owner: JobOwner, source: String): Unit = unsupported
  override def evaluateCommands(owner: JobOwner, source: String, waitForCompletion: Boolean): Unit = unsupported
  override def evaluateCommands(owner: JobOwner, source: String, agent: Agent, waitForCompletion: Boolean): Unit =
    unsupported
  override def evaluateCommands(owner: JobOwner, source: String, agents: AgentSet, waitForCompletion: Boolean): Unit =
    unsupported
  override def evaluateReporter(owner: JobOwner, source: String): AnyRef = unsupported
  override def evaluateReporter(owner: JobOwner, source: String, agent: Agent): AnyRef = unsupported
  override def evaluateReporter(owner: JobOwner, source: String, agents: AgentSet): AnyRef = unsupported
  override def compileCommands(source: String): Procedure = unsupported
  override def compileCommands(source: String, agentClass: Class[_ <: Agent]): Procedure = unsupported
  override def compileReporter(source: String): Procedure = unsupported
  override def runCompiledCommands(owner: JobOwner, procedure: Procedure): Boolean = unsupported
  override def runCompiledReporter(owner: JobOwner, procedure: Procedure): AnyRef = unsupported
  override def dispose(): Unit = unsupported
  override def patchSize: Double = unsupported
  override def changeTopology(wrapX: Boolean, wrapY: Boolean): Unit = unsupported
  override def open(modelPath: String): Unit = unsupported
  override def openString(modelContents: String): Unit = unsupported
  override def magicOpen(name: String): Unit = unsupported
  override def changeLanguage(): Unit = unsupported
  override def openIndex(): Unit = unsupported
  override def openNext(): Unit = unsupported
  override def openPrevious(): Unit = unsupported
  override def startLogging(properties: String): Unit = unsupported
  override def zipLogFiles(filename: String): Unit = unsupported
  override def deleteLogFiles(): Unit = unsupported
  override def getIsApplet: Boolean = unsupported
  override def compiler: CompilerInterface = unsupported
  override def isHeadless: Boolean = unsupported
  override def behaviorSpaceRunNumber: Int = unsupported
  override def behaviorSpaceRunNumber(n: Int): Unit = unsupported
  override def lastLogoException: LogoException = unsupported
  override def clearLastLogoException(): Unit = unsupported
  override def lastRunTimes: WeakHashMap[Job, WeakHashMap[Agent, WeakHashMap[Command, MutableLong]]] = unsupported
  override def completedActivations: WeakHashMap[Activation, JBoolean] = unsupported
  override def profilingEnabled: Boolean = unsupported
  override def profilingTracer: Tracer = unsupported
  override def periodicUpdate(): Unit = unsupported
  override def updateDisplay(haveWorldLockAlready: Boolean): Unit = unsupported
  override def ownerFinished(owner: JobOwner): Unit = unsupported
  override def runtimeError(owner: JobOwner, context: Context, instruction: Instruction,
                            ex: Exception): Unit = unsupported
  override def auxRNG: MersenneTwisterFast = unsupported
  override def mainRNG: MersenneTwisterFast = unsupported
  override def importExtensionData(name: String, data: JList[Array[String]],
                                    handler: ImportErrorHandler): Unit = unsupported
  override def isExtensionName(name: String): Boolean = unsupported
  override def getPlot(plot: String): PlotInterface = unsupported
  override def currentPlot(plot: String): Unit = unsupported
  override def setOutputAreaContents(text: String): Unit = unsupported
  override def setDimensions(dim: WorldDimensions, patchSize: Double): Unit = unsupported
  override def setDimensions(dim: WorldDimensions): Unit = unsupported
  override def patchSize(patchSize: Double): Unit = unsupported
  override def resizeView(): Unit = unsupported
  override def findProcedurePositions(source: String): JMap[String, JList[AnyRef]] = unsupported
  override def getTokenAtPosition(source: String, position: Int): Token = unsupported
  override def tokenizeForColorization(source: String): Array[Token] = unsupported
  override def isValidIdentifier(s: String): Boolean = unsupported
  override def isReporter(s: String): Boolean = unsupported
  override def checkCommandSyntax(source: String): Unit = unsupported
  override def checkReporterSyntax(source: String): Unit = unsupported
  override def readNumberFromString(source: String): AnyRef = unsupported
  override def autoConvert(source: String, subprogram: Boolean, reporter: Boolean,
                            modelVersion: String): String = unsupported
  override def readFromString(s: String): AnyRef = unsupported
  override def isConstant(s: String): Boolean = unsupported

  private def unsupported = throw new UnsupportedOperationException
}
