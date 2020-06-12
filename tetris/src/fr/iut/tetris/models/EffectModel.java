package fr.iut.tetris.models;

import fr.iut.tetris.Config;
import fr.iut.tetris.controllers.VersusController;
import fr.iut.tetris.Log;
import fr.iut.tetris.enums.Direction;
import fr.iut.tetris.enums.GameState;
import fr.iut.tetris.exceptions.OverlappedPieceException;
import fr.iut.tetris.exceptions.PieceOutOfBoardException;
import fr.iut.tetris.vues.Common;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

class BonusSpeed extends EffectModel {
    public BonusSpeed(VersusModel model, int player, int duration) {
        super("/res/effects/bonus_speed.png");
        Timer timer = new Timer(duration, actionEvent -> {
            if (player == 0)
                model.effectListPlayerA.remove(this);
            if (player == 1)
                model.effectListPlayerB.remove(this);
        });
        timer.setRepeats(false);
        timer.start();
    }

    @Override
    public int speedFunction(int currentSpeed) {
        return currentSpeed + 500;
    }
}

class MalusSpeed extends EffectModel {
    public MalusSpeed(VersusModel model, int player, int duration) {
        super("/res/effects/malus_speed.png");
        Timer timer = new Timer(duration, actionEvent -> {
            if (player == 0)
                model.effectListPlayerA.remove(this);
            if (player == 1)
                model.effectListPlayerB.remove(this);
        });
        timer.setRepeats(false);
        timer.start();
    }

    @Override
    public int speedFunction(int currentSpeed) {
        return Math.max(currentSpeed - 500,10);
    }
}

class RandomLine extends EffectModel {
    VersusModel model;
    RandomLine tmp;
    int player;
    public RandomLine(VersusModel model, int player) {
        super("/res/effects/bonus_delete_line.png");
        this.model = model;
        this.player = player;
        this.tmp = this;

        Timer timer = new Timer(new Random().nextInt(2000)+2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                doEffect();
            }
        });
        timer.setRepeats(false); // Only execute once
        timer.start(); // Go go go!
    }

    void doEffect() {
        BlockModel[][] models;
        if(player==0){
            this.model.effectListPlayerA.remove(tmp);
            try {
                models = this.model.computeMixedGrid(0);
            } catch (OverlappedPieceException | PieceOutOfBoardException e) {
                e.printStackTrace();
                models = null;
            }
        } else {
            this.model.effectListPlayerB.remove(tmp);
            try {
                models = this.model.computeMixedGrid(1);
            } catch (OverlappedPieceException | PieceOutOfBoardException e) {
                e.printStackTrace();
                models = null;
            }
        }

        if(models != null) {
            int maxY = models.length - 1;
            for (int y = models.length-1; y >= 0 ; y--) {
                boolean lineEmpty = true;
                for (BlockModel m: models[y]) {
                    if(m!=null) {
                        maxY = y;
                        lineEmpty = false;
                        break;
                    }
                }
                if(lineEmpty) break;
            }

            int y = new Random().nextInt(((model.height-1) - maxY) + 1) + maxY;


            for (BlockModel block: models[y]) {
                models[y] = null;
                if (player == 0) { model.pieceListPlayerA.remove(block); }
                else { model.pieceListPlayerB.remove(block); }
            }

            for (int fally = y-1; fally >= 0; fally--) {
                for (BlockModel block: models[fally]) {
                    if(block != null) {
                        Log.debug(this, "Falling line " + fally + " to "+(fally+1)+" (x:"+block.standAlonePos.x+")");
                        block.standAlonePos.y = Math.min( (fally+1) , model.height - 1);
                    }
                }
            }

            this.model.checkForFullLineAndRemoveIt(true,player);
            Log.debug(this,maxY);
        }
    }
}

class InvertControls extends EffectModel {
    VersusModel model;
    int player;

    public InvertControls(VersusModel model, int player, int duration){
        super("/res/effects/malus_reverse_cmds.png");
        this.model = model;
        this.player = player;
        Timer timer = new Timer(duration, actionEvent -> {
            if (this.player == 0)
                model.effectListPlayerA.remove(this);
            if (this.player == 1)
                model.effectListPlayerB.remove(this);
        });
        timer.setRepeats(false);
        timer.start();
    }

    @Override
    public KeyEvent keyDown(KeyEvent e) {
        if(player == 1) {
            if(e.getKeyCode()== Config.getInstance().getInt("KEYCODE_P1_LEFT")) {
                e.setKeyCode(Config.getInstance().getInt("KEYCODE_P1_RIGHT"));
                return e;
            }
            if(e.getKeyCode()==Config.getInstance().getInt("KEYCODE_P1_RIGHT")) {
                e.setKeyCode(Config.getInstance().getInt("KEYCODE_P1_LEFT"));
                return e;
            }
        }
        if(player == 0) {
            if(e.getKeyCode()== Config.getInstance().getInt("KEYCODE_P2_LEFT")) {
                e.setKeyCode(Config.getInstance().getInt("KEYCODE_P2_RIGHT"));
                return e;
            }
            if(e.getKeyCode()==Config.getInstance().getInt("KEYCODE_P2_RIGHT")) {
                e.setKeyCode(Config.getInstance().getInt("KEYCODE_P2_LEFT"));
                return e;
            }
        }
        return e;
    }
}

class RandomRotation extends EffectModel {
    VersusModel model;
    int player;

    public RandomRotation(VersusModel model, int player, int duration){
        super("/res/effects/malus_rotate.png");
        this.model = model;
        this.player = player;
        Timer timer = new Timer(duration, actionEvent -> {
            if (this.player == 0)
                model.effectListPlayerA.remove(this);
            if (this.player == 1)
                model.effectListPlayerB.remove(this);
        });
        timer.setRepeats(false);
        timer.start();
    }

    @Override
    public KeyEvent keyDown(KeyEvent e){
        if (this.player == 0){
            if (e.getKeyCode()==Config.getInstance().getInt("KEYCODE_P2_ROTATE")) {
                int n = new Random().nextInt(4);
                for (int i = 0; i < n; i++)
                    model.rotateCurrent(0,Direction.RIGHT);
                return e;
            }
        }
        if (this.player == 1){
            if (e.getKeyCode()==Config.getInstance().getInt("KEYCODE_P1_ROTATE")) {
                int n = new Random().nextInt(4);
                for (int i = 0; i < n; i++)
                    model.rotateCurrent(1,Direction.RIGHT);
                return e;
            }
        }
        return e;
    }
}





class HideNextPiece extends EffectModel {
	
	public HideNextPiece(VersusModel model, int player, int duration) {
        super("/res/effects/malus_blind.png");
        
        if (player == 0)
            model.hideNextPieceA = true;
        if (player == 1)
            model.hideNextPieceB = true;
        
        Timer timer = new Timer(duration, actionEvent -> {
            if (player == 0){
                model.hideNextPieceA = false;
            	model.effectListPlayerA.remove(this);
            }
            if (player == 1){
                model.hideNextPieceB = false;
            	model.effectListPlayerB.remove(this);
            } 
        });
        timer.setRepeats(false);
        timer.start();
    }

}




public class EffectModel {
    public String imagePath;
    public BufferedImage image;

    public EffectModel(String imagePath) {
        this.imagePath = imagePath;
        this.image = Config.getInstance().getRessourceImage(imagePath);
    }

    public int speedFunction(int currentSpeed) {
        return currentSpeed;
    }

    public KeyEvent keyDown(KeyEvent e) {
        return e;
    }
}
