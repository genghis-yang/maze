package info.genghis.game.maze

import indigo._
import scala.scalajs.js.annotation.JSExportTopLevel

@JSExportTopLevel("IndigoGame")
object Maze extends IndigoSandbox[BrickWall, Model] {
  val unit = 12
  val magnification = 2

  val config: indigo.GameConfig =
    GameConfig.default.withMagnification(magnification)

  val animations: Set[Animation] =
    Set()

  val assetName = AssetName("images")

  val assets: Set[indigo.AssetType] = Set(
    AssetType.Image(assetName, AssetPath("assets/images.png"))
  )

  val fonts: Set[FontInfo] =
    Set()

  def setup(
      assetCollection: AssetCollection,
      dice: Dice
  ): Startup[StartupErrors, BrickWall] =
    Startup.Success(
      BrickWall(
        List(
          Point(0, 0),
          Point(1, 0),
          Point(2, 3),
          Point(3, 3)
        )
      )
    )

  def initialModel(startupData: BrickWall): Model =
    Model(Point(0, 1))

  def updateModel(
      context: FrameContext[BrickWall],
      model: Model
  ): GlobalEvent => Outcome[Model] = {
    case KeyboardEvent.KeyUp(Keys.UP_ARROW) => {
      val newPosition = model.position - Point(0, 1)
      Outcome(if (!context.startUpData.bricks.contains(newPosition)) {
        model.update(newPosition)
      } else {
        model
      })
    }
    case KeyboardEvent.KeyUp(Keys.DOWN_ARROW) => {
      val newPosition = model.position + Point(0, 1)
      Outcome(if (!context.startUpData.bricks.contains(newPosition)) {
        model.update(newPosition)
      } else {
        model
      })
    }
    case KeyboardEvent.KeyUp(Keys.LEFT_ARROW) => {
      val newPosition = model.position - Point(1, 0)
      Outcome(if (!context.startUpData.bricks.contains(newPosition)) {
        model.update(newPosition)
      } else {
        model
      })
    }
    case KeyboardEvent.KeyUp(Keys.RIGHT_ARROW) => {
      val newPosition = model.position + Point(1, 0)
      Outcome(if (!context.startUpData.bricks.contains(newPosition)) {
        model.update(newPosition)
      } else {
        model
      })
    }
    case _ =>
      Outcome(model)
  }

  val brick = Graphic(Rectangle(0, 0, 32, 32), 1, Material.Textured(assetName))
    .withCrop(Rectangle(0, 0, unit, unit))
  val role = Graphic(Rectangle(0, 0, 32, 32), 1, Material.Textured(assetName))
    .withCrop(Rectangle(unit, 0, unit, unit))

  def present(
      context: FrameContext[BrickWall],
      model: Model
  ): SceneUpdateFragment =
    SceneUpdateFragment.empty.addGameLayerNodes(
      drawWall(context.startUpData.bricks) :+ drawRole(model.position)
    )

  def drawWall(bricks: List[Point]): List[Graphic] =
    bricks.map(position => brick.moveTo(position * unit))

  def drawRole(position: Point): Graphic =
    role.moveTo(position * unit)

}

case class Model(position: Point) {
  def update(newPosition: Point): Model = this.copy(position = newPosition)
}

case class BrickWall(bricks: List[Point])
