//package cena;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.util.gl2.GLUT;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author siabr
 */
public class Cena implements GLEventListener, KeyListener {

    private float transladeP1X, transladeP1Y = -85.2f,
            tX = 0f, tY = 0f, velX = 2f, velY = 2f;
    private int vidas = 3;
    private boolean startGame = false, credits = false;

    private boolean lostLifeFlag = true;
    private int points = 0;
    private boolean swapFase = false;
    int stage = 1;
    private boolean lose = false;

    @Override
    public void init(GLAutoDrawable drawable) {
        //dados iniciais da cena
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        //obtem o contexto Opengl
        GL2 gl = drawable.getGL().getGL2();
        //define a cor da janela (R, G, G, alpha)
        gl.glClearColor(0, 0, 0, 1);
        //limpa a janela com a cor especificada
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity(); //lê a matriz identidade

        /*
            desenho da cena        
        *
         */
        //Menu Start Game || Credits
        if (!startGame && !credits) {
            gl.glPushMatrix();
            gl.glColor3f(1, 1, 1);
            gl.glTranslatef(-60f, 0, 0);
            gl.glScalef(0.072f, 0.072f, 0);
            drawText(gl, -20, 0, "PRESS ENTER TO START");
            gl.glPopMatrix();

            gl.glPushMatrix();
            gl.glColor3f(1, 1, 1);
            gl.glTranslatef(-30f, -10, 0);
            gl.glScalef(0.042f, 0.042f, 0);
            drawText(gl, -20, 0, "press c to credits ");
            gl.glPopMatrix();
        }
        //Creditos
        if (!startGame && credits) {
            gl.glPushMatrix();
            gl.glColor3f(1, 1, 1);
            gl.glTranslatef(-60f, 30, 0);
            gl.glScalef(0.072f, 0.072f, 0);
            drawText(gl, -60, 0, "Diogo Casagrande\n");
            gl.glPopMatrix();

            gl.glPushMatrix();
            gl.glColor3f(1, 1, 1);
            gl.glTranslatef(-40f, 20, 0);
            gl.glScalef(0.072f, 0.072f, 0);
            drawText(gl, -60, 0, "R.A.:20718678\n");
            gl.glPopMatrix();

            gl.glPushMatrix();
            gl.glColor3f(1, 1, 1);
            gl.glTranslatef(-60f, 10, 0);
            gl.glScalef(0.072f, 0.072f, 0);
            drawText(gl, -60, 0, "----------------\n");
            gl.glPopMatrix();

            gl.glPushMatrix();
            gl.glColor3f(1, 1, 1);
            gl.glTranslatef(-60f, 0, 0);
            gl.glScalef(0.072f, 0.072f, 0);
            drawText(gl, -60, 0, "VICTOR MARTINS PENNA\n");
            gl.glPopMatrix();

            gl.glPushMatrix();
            gl.glColor3f(1, 1, 1);
            gl.glTranslatef(-40f, -10, 0);
            gl.glScalef(0.072f, 0.072f, 0);
            drawText(gl, -60, 0, "R.A.:20537083\n");
            gl.glPopMatrix();

            gl.glPushMatrix();
            gl.glColor3f(1, 1, 1);
            gl.glTranslatef(-45f, -30, 0);
            gl.glScalef(0.042f, 0.042f, 0);
            drawText(gl, -20, 0, "press backspace to go back ");
            gl.glPopMatrix();
        }
        
        //troca para fase 2
        if (swapFase) {
            gl.glPushMatrix();
            gl.glColor3f(1, 1, 1);
            gl.glTranslatef(-60f, 30, 0);
            gl.glScalef(0.072f, 0.072f, 0);
            drawText(gl, -60, 0, "Stage " + String.valueOf(stage) + " press ENTER\n");
            gl.glPopMatrix();
            
            points = 0;
            transladeP1X = 0;
            vidas = 3;
            tX = 0;
            tY = 100;
            velX += 0.005; 
            velY += 0.005;
        }

        if (startGame && vidas > 0 && swapFase == false && lose == false) {
            gl.glPushMatrix();
            gl.glColor3f(1, 1, 1);
            gl.glTranslatef(-95f, 95, 0);
            gl.glScalef(0.032f, 0.032f, 0);
            drawText(gl, 0, 0, "vidas");
            gl.glPopMatrix();

            if (vidas >= 1) {
                gl.glPushMatrix();
                drawLifeb(gl);
                gl.glPopMatrix();
            }
            if (vidas >= 2) {
                gl.glPushMatrix();
                gl.glTranslatef(4f, 0, 0);
                drawLifeb(gl);
                gl.glPopMatrix();
            }
            if (vidas == 3) {
                gl.glPushMatrix();
                gl.glTranslatef(8f, 0, 0);
                drawLifeb(gl);
                gl.glPopMatrix();
            }

            gl.glPushMatrix();
            gl.glColor3f(1, 1, 1);
            gl.glTranslatef(0f, 95, 0);
            gl.glScalef(0.032f, 0.032f, 0);
            drawText(gl, 0, 0, "Pontos");
            gl.glPopMatrix();

            gl.glPushMatrix();
            gl.glColor3f(1, 1, 1);
            gl.glTranslatef(10f, 90, 0);
            gl.glScalef(0.032f, 0.032f, 0);
            drawText(gl, 0, 0, String.valueOf(points));
            gl.glPopMatrix();
            
            gl.glPushMatrix();
            gl.glColor3f(1, 1, 1);
            gl.glTranslatef(0f, 85, 0);
            gl.glScalef(0.032f, 0.032f, 0);
            drawText(gl, 0, 0, "Stage : "+ String.valueOf(stage));
            gl.glPopMatrix();

            gl.glPushMatrix();
            gl.glTranslatef(transladeP1X, 0, 0);
            gl.glColor3f(0, 0.112f, 0.532f);
            drawPlayer1(gl);
            gl.glPopMatrix();

            if (lostLifeFlag) {
                lostLifeFlag = false;
                tX = 0;
                tY = 100;
                gl.glPushMatrix();
                gl.glColor3f(1, 1, 1);
                movBallX();
                gl.glTranslatef(transladeP1X, 100, 0);
                drawBall(gl);
                gl.glPopMatrix();
            } else {
                gl.glPushMatrix();
                gl.glColor3f(1, 1, 1);
                movBallX();
                gl.glTranslatef(tX = tX + velX, tY = tY + velY, 0);
                drawBall(gl);
                gl.glPopMatrix();
            }

        }
        //Tela You Lose
        if (lose) {
            gl.glPushMatrix();
            gl.glColor3f(1, 1, 1);
            gl.glTranslatef(-30f, 0, 0);
            gl.glScalef(0.072f, 0.072f, 0);
            drawText(gl, -20, 0, "GAME OVER");
            gl.glPopMatrix();

            gl.glPushMatrix();
            gl.glColor3f(1, 1, 1);
            gl.glTranslatef(-30f, -10, 0);
            gl.glScalef(0.042f, 0.042f, 0);
            drawText(gl, -20, 0, "press R to restart ");
            gl.glPopMatrix();
            
            stage = 1;
            points = 0;
            transladeP1X = 0;
            vidas = 3;
            tX = 0;
            tY = 100;
            velX = 2; 
            velY = 2;
        }
        gl.glFlush();

    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        //obtem o contexto grafico Opengl
        GL2 gl = drawable.getGL().getGL2();
        //ativa a matriz de projeção
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity(); //lê a matriz identidade
        //projeção ortogonal (xMin, xMax, yMin, yMax, zMin, zMax)
        gl.glOrtho(-100, 100, -100, 100, -100, 100);
        //ativa a matriz de modelagem
        gl.glMatrixMode(GL2.GL_MODELVIEW);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        System.out.println(e.getKeyCode());
        if (startGame) {
            switch (e.getKeyCode()) {
                //move pra esquerda
                case 149:
                    if (transladeP1X > -60f) {
                        transladeP1X -= 10f;
                    }
                    break;
                //move pra direita
                case 151:
                    if (transladeP1X < 80f) {
                        transladeP1X += 10f;
                    }
                    break;
                case 82:
                    if(lose == true){
                        lose = false;
                    }
                    break;
                case 48:
                    System.exit(0);
            }
        }
        char keyChar = e.getKeyChar();

        switch (keyChar) {
            case KeyEvent.VK_ENTER:
                startGame = true;
                if(swapFase == true){
                    swapFase = false;
                }
                break;
            case 'c':
                credits = true;
                break;
            case KeyEvent.VK_BACK_SPACE:
                credits = false;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public void drawText(GL2 gl, int x, int y, String frase) {
        GLUT glut = new GLUT();
        gl.glRasterPos2f(x, y);
        glut.glutStrokeString(GLUT.STROKE_ROMAN, frase);

    }

    public void drawPlayer1(GL2 gl) {
        gl.glBegin(GL2.GL_POLYGON);

        gl.glVertex2f(-40f, -85.2f);
        gl.glVertex2f(20f, -85.2f);
        gl.glVertex2f(20f, -90f);
        gl.glVertex2f(-40f, -90f);
        gl.glEnd();
    }

    public void drawBall(GL2 gl) {

        gl.glBegin(GL2.GL_POLYGON);

        gl.glVertex2f(-1, -85.2f);
        gl.glVertex2f(3, -85.2f);
        gl.glVertex2f(3, -81.2f);
        gl.glVertex2f(-1, -81.2f);
        gl.glEnd();
    }

    public void drawLifeb(GL2 gl) {

        gl.glBegin(GL2.GL_POLYGON);

        gl.glVertex2f(-95, 90f);
        gl.glVertex2f(-93, 90f);
        gl.glVertex2f(-93, 92f);
        gl.glVertex2f(-95, 92f);
        gl.glEnd();
    }

    public void movBallX() {

        if (tX >= 98 || tX <= -99) {
            velX = -velX;
//            System.out.println(tX);
        }
        if (tY >= 182) {
            velY = -velY;
        }
        if (tY <= -16) {
            velY = -velY;
            vidas--;
            lostLifeFlag = true;
            if(vidas <= 0)
                lose = true;
        }
        if (colisaoPlayer()) {
            points += 1;
            velY = -velY;
            if(points >= stage){
                swapFase = true;
                stage += 1;
            }
        } else if (colisao()) {
            velY = -velY;
        }
    }

    public boolean colisao() {
        if ((tX <= transladeP1X + 30
                && tX >= transladeP1X - 30
                && tY == -85f)) {
            return true;
        }
        return false;
    }

    public boolean colisaoPlayer() {
        if ((tX > transladeP1X - 40 && tX < transladeP1X + 20)
                && (tY < 0 && tY > -10)) {
            return true;
        }
        return false;
    }

}
