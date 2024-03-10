package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyHandler;

    public Player(GamePanel gp, KeyHandler keyHandler) {
        this.gp = gp;
        this.keyHandler = keyHandler;

        this.setDefaultValues();
        this.getPlayerImage();
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {

        try {
            // @TODO think about null warning
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void update() {

        //
        boolean up = keyHandler.upPressed;
        boolean down = keyHandler.downPressed;
        boolean left = keyHandler.leftPressed;
        boolean right = keyHandler.rightPressed;


        // alter sprites only when moving (or pressing opposite keys)
        if (up || down || right || left) {
            spriteCounter++;
        }

        // don't move if pressing opposite keys
        if (left && right) {
            left = false;
            right = false;
        }

        if (up && down) {
            up = false;
            down = false;
        }

        if (up && right) {
            y -= (int) (speed * 0.7);
            x += (int) (speed * 0.7);
            direction = "right";
        } else if (up && left) {
            y -= (int) (speed * 0.7);
            x -= (int) (speed * 0.7);
            direction = "left";
        } else if (down && right) {
            y += (int) (speed * 0.7);
            x += (int) (speed * 0.7);
            direction = "right";
        } else if (down && left) {
            y += (int) (speed * 0.7);
            x -= (int) (speed * 0.7);
            direction = "left";

        } else if (up) {
            y -= speed;
            direction = "up";
        } else if (down) {
            y += speed;
            direction = "down";
        } else if (left) {
            x -= speed;
            direction = "left";
        } else if (right) {
            x += speed;
            direction = "right";
        }

        if (spriteCounter > 12) {
            spriteNum = (spriteNum == 1)?2:1;
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2d) {

//        g2d.setColor(Color.WHITE);
//        g2d.fillRect(x, y, gp.tileSize, gp.tileSize);

        BufferedImage image = null;

        if (spriteNum == 1) {
            switch (direction) {
                case "up":
                    image = up1;
                    break;
                case "down":
                    image = down1;
                    break;
                case "right":
                    image = right1;
                    break;
                case "left":
                    image = left1;
                    break;
            }
        } else {
            switch (direction) {
                case "up":
                    image = up2;
                    break;
                case "down":
                    image = down2;
                    break;
                case "right":
                    image = right2;
                    break;
                case "left":
                    image = left2;
                    break;

            }
        }

        g2d.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);

    }
}
