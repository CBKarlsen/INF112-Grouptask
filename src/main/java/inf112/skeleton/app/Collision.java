package inf112.skeleton.app;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import inf112.skeleton.app.Entities.AbstractGameObject;
import inf112.skeleton.app.Entities.Player.Player;
import inf112.skeleton.app.Mapfolder.GrassMini;
import inf112.skeleton.app.Mapfolder.House;
import inf112.skeleton.app.Mapfolder.Level1Mini;
import inf112.skeleton.app.Mapfolder.MapInterface;

public class Collision {

    public MapInterface nextMap;
    public MapInterface currMap;
    private TiledMap map;

    private AbstractGameObject entity;

    private float tileSize;
    private float posX, posY;

    public Collision(MapInterface currMap, AbstractGameObject entity) {
        this.currMap = currMap;
        this.map = currMap.getMap();
        this.entity = entity;
        tileSize = ((TiledMapTileLayer) this.map.getLayers().get(0)).getTileWidth();
    }

    /**
     * Checks collision in X-direction
     * 
     * @param velX velocity of object in X direction
     * @return false if not collision. True if collision.
     */
    public boolean checkXDirection(float velX) {
        boolean collisionX = false;
        posX = entity.getPosition().x;
        posY = entity.getPosition().y;

        // when moving to the left
        if (velX < 0) {
            // top left tile
            collisionX = isCellBlocked((int) (posX + velX / tileSize),
                    (int) ((posY + entity.getHeight() - (entity.getHeight() / 4)) / tileSize));

            // middle left tile
            if (!collisionX)
                collisionX = isCellBlocked((int) ((posX + velX) / tileSize),
                        (int) ((posY + entity.getHeight() / 2) / tileSize));

            // bottom left tile
            if (!collisionX)
                collisionX = isCellBlocked((int) (posX + velX / tileSize),
                        (int) (((posY + entity.getHeight() / 4) / tileSize)));

        }

        // when moving to the right
        else if (velX > 0) {
            // top right tile
            collisionX = isCellBlocked((int) ((posX + velX + entity.getWidth()) / tileSize),
                    (int) ((posY + entity.getHeight() - (entity.getHeight() / 4)) / tileSize));

            // middle right tile
            if (!collisionX)
                collisionX = isCellBlocked((int) ((posX + velX + entity.getWidth()) / tileSize),
                        (int) ((posY + entity.getHeight() / 2) / tileSize));

            // bottom right
            if (!collisionX)
                collisionX = isCellBlocked((int) ((posX + velX + entity.getWidth()) / tileSize),
                        (int) ((posY + entity.getHeight() / 4) / tileSize));
        }

        if (collisionX)
            return true;
        return false;

    }

    /**
     * Checks collision in Y-direction
     * 
     * @param velY velocity of object in Y direction
     * @return false if not collision. True if collision.
     */
    public boolean checkYDirection(float velY) {
        boolean collisionY = false;
        posX = entity.getPosition().x;
        posY = entity.getPosition().y;
        // when moving downwards
        if (velY < 0) {
            // bottom left
            collisionY = isCellBlocked((int) ((posX + entity.getWidth() - (entity.getWidth() / 4)) / tileSize),
                    (int) ((posY + velY) / tileSize));

            // bottom middle
            if (!collisionY)
                collisionY = isCellBlocked((int) ((posX + (entity.getWidth() / 2)) / tileSize),
                        (int) ((posY + velY) / tileSize));

            // bottom right
            if (!collisionY)
                collisionY = isCellBlocked((int) ((posX + entity.getWidth() / 4) / tileSize),
                        (int) ((posY + velY) / tileSize));

        }
        // moving upwards
        else if (velY > 0) {

            // top left
            collisionY = isCellBlocked((int) ((posX + entity.getWidth() - (entity.getWidth() / 4)) / tileSize),
                    (int) ((posY + velY + entity.getHeight()) / tileSize));

            // top middle
            if (!collisionY)
                collisionY = isCellBlocked((int) ((posX + (entity.getWidth() / 2)) / tileSize),
                        (int) ((posY + velY + entity.getHeight()) / tileSize));
            // top right
            if (!collisionY)
                collisionY = isCellBlocked((int) ((posX + entity.getWidth() / 4) / tileSize),
                        (int) ((posY + velY + entity.getHeight()) / tileSize));
        }
        if (collisionY)
            return true;
        return false;

    }

    private boolean isCellBlocked(int xpos, int ypos) {

        int size = map.getLayers().size();

        for (int i = 0; i < size; i++) {
            TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(i);
            TiledMapTileLayer.Cell cell = layer.getCell(xpos, ypos);

            if (cell != null && cell.getTile().getProperties().containsKey("blocked")) {
                return true;
            }

        }
        return false;
    }

    /**
     * @return true if entity is a player and collides with a portal. False if not
     */
    public boolean isCellAPortal() {

        if (entity instanceof Player) {

            int size = map.getLayers().size();

            float entityX = (entity.getPosition().x + entity.getWidth() / 2) / tileSize;
            float entityY = (entity.getPosition().y + entity.getHeight() / 2) / tileSize;

            for (int i = 0; i < size; i++) {

                TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(i);

                TiledMapTileLayer.Cell entityCell = layer.getCell((int) entityX, (int) entityY);

                if (entityCell != null && entityCell.getTile().getProperties().containsKey("portal")) {

                    // This is for the map cycle
                    if (entityCell.getTile().getProperties().containsKey("house")) {
                        nextMap = new House();
                        currMap.stopMusic();
                    }

                    if (entityCell.getTile().getProperties().containsKey("level 2")) {
                        nextMap = new Level1Mini(113, 75);
                        currMap.stopMusic();
                    }

                    if (entityCell.getTile().getProperties().containsKey("grass")) {
                        nextMap = new GrassMini(119, 52);
                        currMap.stopMusic();
                    }

                    if (entityCell.getTile().getProperties().containsKey("mini level 1")) {

                        if (currMap.getAllEnemiesDead()) {
                            nextMap = new Level1Mini(139, 70);
                            currMap.stopMusic();
                        } else {
                            return false;
                        }

                    }

                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @returns map associated with collision.
     */
    public TiledMap getMap() {
        return map;
    }

    /**
     * Sets map associated with collision.
     * 
     * @param map
     */
    public void setMap(TiledMap map) {
        this.map = map;
    }
}
