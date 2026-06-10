package org.nlogo.convert

import org.nlogo.api.{ Approximate, Version, WorldDimensions, WorldDimensions3D }
import org.nlogo.nvm.Workspace
import org.nlogo.util.MersenneTwisterFast
import org.nlogo.window.Widget
import org.nlogo.workspace.{ WorldLoader, WorldLoader3D, WorldLoaderInterface  }

private class HeadlessViewWidget extends Widget with WorldLoaderInterface {
  private var threed = false
  private var patchSize = 0.0
  private var fontSize = 0
  private var wrapX = true
  private var wrapY = true
  private var minPxcor = 0
  private var maxPxcor = 0
  private var minPycor = 0
  private var maxPycor = 0
  private var minPzcor = 0
  private var maxPzcor = 0
  private var updateMode: Workspace.UpdateMode = Workspace.UpdateMode.CONTINUOUS
  private var showTick = true
  private var tickLabel = ""
  private var frames = 0.0

  override def load(lines: Array[String], helper: Widget.LoadHelper): AnyRef = {
    threed = Version.is3D(helper.version)

    val loader: WorldLoader = {
      if (threed) {
        new WorldLoader3D
      } else {
        new WorldLoader
      }
    }

    loader.load(lines, helper.version, this)

    this
  }

  override def save: String = {
    "GRAPHICS-WINDOW\n" +
    getX + '\n' +
    getY + '\n' +
    (getX + getWidth).toString + '\n' +
    (getY + getHeight).toString + '\n' +
    (if (-minPxcor == maxPxcor) maxPxcor else -1) + '\n' +
    (if (-minPycor == maxPycor) maxPycor else -1) + '\n' +
    patchSize + '\n' +
    "1\n" +
    fontSize + '\n' +
    "1\n" +
    "1\n" +
    "1\n" +
    "0\n" +
    (if (wrapX) "1" else "0") + '\n' +
    (if (wrapY) "1" else "0") + '\n' +
    "1\n" +
    minPxcor + '\n' +
    maxPxcor + '\n' +
    minPycor + '\n' +
    maxPycor + '\n' +
    (if (threed) (minPzcor + "\n" + maxPzcor + '\n') else "") +
    (if (threed) "1\n" else "") +
    updateMode.save + "\n" +
    (if (threed) "" else (updateMode.save + "\n")) +
    (if (showTick) "1" else "0") + '\n' +
    tickLabel + '\n' +
    frames + '\n'
  }

  override def patchSize(size: Double): Unit = {
    patchSize = size
  }

  override def setDimensions(dims: WorldDimensions, size: Double): Unit = {
    minPxcor = dims.minPxcor
    maxPxcor = dims.maxPxcor
    minPycor = dims.minPycor
    maxPycor = dims.maxPycor

    dims match {
      case threed: WorldDimensions3D =>
        minPzcor = threed.minPzcor
        maxPzcor = threed.maxPzcor

      case _ =>
    }

    patchSize(size)
  }

  override def fontSize(size: Int): Unit = {
    fontSize = size
  }

  override def changeTopology(x: Boolean, y: Boolean): Unit = {
    wrapX = x
    wrapY = y
  }

  override def clearTurtles(): Unit = {}

  override def updateMode(mode: Workspace.UpdateMode): Unit = {
    updateMode = mode
  }

  override def getMinimumWidth: Int =
    0

  override def computePatchSize(width: Int, patches: Int): Double = {
    var exactPatchSize = width.toDouble / patches

    (0 until 15).map(precision => patches * Approximate.approximate(exactPatchSize, precision))
      .find(_.toInt == width).getOrElse(exactPatchSize)
  }

  override def calculateHeight(worldHeight: Int, patchSize: Double): Int =
    (patchSize * worldHeight).toInt + 21

  override def calculateWidth(worldWidth: Int, patchSize: Double): Int =
    (patchSize * worldWidth).toInt

  override def insetWidth(): Int =
    0

  override def tickCounterLabel(label: String): Unit = {
    tickLabel = label
  }

  override def tickCounterLabel: String =
    tickLabel

  override def showTickCounter(visible: Boolean): Unit = {
    showTick = visible
  }

  override def showTickCounter: Boolean =
    showTick

  override def frameRate: Double =
    frames

  override def frameRate(rate: Double): Unit = {
    frames = rate
  }
}
