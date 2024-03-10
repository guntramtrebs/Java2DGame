package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class TileManager {
    GamePanel gp;
    Tile[] tile;

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[10];

        getTileImage();
    }

    private void getTileImage() {

        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics2D g2d) {




        g2d.drawImage(tile[0].image, 0, 0, gp.tileSize, gp.tileSize, null);
        g2d.drawImage(tile[1].image, 48, 0, gp.tileSize, gp.tileSize, null);
        g2d.drawImage(tile[2].image, 96, 0, gp.tileSize, gp.tileSize, null);




            for (int column = 0; column < 16; column++) {

                for (int row = 0; row < 16; row++) {
                    g2d.drawImage(tile[ TileLayout.layout[row][column] ].image, column*gp.tileSize, row * gp.tileSize, gp.tileSize, gp.tileSize, null);

                }
            }

    }
}
