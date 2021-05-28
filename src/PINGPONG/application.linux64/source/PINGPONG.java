import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.sound.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class PINGPONG extends PApplet {


SoundFile hsound, wsound, vsound;
Ball ball;
Paddle p1, p2;
Bonus bonus;
Bonus2 bonus2;
PImage img;
float xspeed, yspeed;
boolean play, rwin, lwin;
float xpos, ypos, wdth, ht;
int fscore1, fscore2;
//Setup
public void setup() {
  
  background(0);
  frameRate(60);
  fscore1 = 0;
  hsound = new SoundFile(this, "hsound.wav");
  vsound = new SoundFile(this, "vsound.wav");
  wsound = new SoundFile(this, "wsound.wav");
  img = loadImage("title.png");
  fscore2 = 0;
  bonus2 = new Bonus2(200, 100);
  bonus = new Bonus(200, 300);
  ball = new Ball(200, 200);
  p1 = new Paddle(25, 200);
  p2 = new Paddle(375, 200);
}
public void draw() {
  //Startscreen check
  if (!play) {
    startScreen();
  }else{
   //win condition check
    if (lwin){
      fill(255);
      text("LEFT WINS", 50, 200);
    }else if(rwin){
      fill(255);
      text("RIGHT WINS", 275, 200);
    }else{
    if (fscore2 == 10){
      lwin = true;
      vsound.play();
    }
    if (fscore1 == 10){
      rwin = true;
      vsound.play();
    }
    if (ball.sc == 1){
     wsound.play();
    }
    background(0);
    fill(0);
    //score system
    fscore1 = ball.score1;
    fscore2 = ball.score2;
    fill(255);
    textMode(LEFT);
    textSize(20);
    text("Left: "+fscore2, 0, 50);
    text("Right: "+fscore1, 300, 50);
    fill(0);
    //Classes
    ball.display();
    ball.move();
    p1.display();
    p1.move();
    p2.display();
    p2.move();
    bonus.move();
    bonus.display();
    bonus2.move();
    bonus2.display();
    //ball and bonus collide detection
    float distance = dist(bonus.x, bonus.y, ball.x, ball.y); 
    if (distance < bonus.rad + ball.rad) { 
      ball.xspeed = 15;
    }
    float distance2 = dist(bonus2.x, bonus2.y, ball.x, ball.y); 
    if (distance2 < bonus2.rad + ball.rad) { 
      ball.xspeed = -15;
    }
    //ball and paddle collide detection
    if ( ball.left() < p1.right() && ball.y > p1.top() && ball.y < p1.bottom()) {
      ball.xspeed = -3;
      ball.xspeed = -ball.xspeed;
      hsound.play();
      ball.yspeed = map(ball.y - p1.y, -p1.h/2, p1.h/2, -4, 4);
    }
    if ( ball.right() > p2.left() && ball.y > p2.top() && ball.y < p2.bottom()) {
      ball.xspeed = 3;
      ball.xspeed = -ball.xspeed;
      hsound.play();
      ball.yspeed = map(ball.y - p2.y, -p2.h/2, p2.h/2, -4, 4);
    }
  }
  }
}
//user input
public void keyPressed() {
  if (key == 'w') {
    p1.yspeed=-8;
  }
  if (key == 's') {
    p1.yspeed=8;
  }
  if (key == 'i') {
    p2.yspeed=-8;
  }
  if (key == 'k') {
    p2.yspeed=8;
  }
}
public void keyReleased() {
  if (key == 'w') {
    p1.yspeed=0;
  }
  if (key == 's') {
    p1.yspeed=0;
  }
  if (key == 'i') {
    p2.yspeed=0;
  }
  if (key == 'k') {
    p2.yspeed=0;
  }
}
//start screen
public void startScreen() {
    background(0);
    textMode(CENTER);
    image(img, 50, 75);
    text("Click to Play", 166, 200);
    text("CONTROLS:", 0, 315);
    text("LEFT: 'w' up, 's' down", 0, 340);
    text("RIGHT: 'i' up, 'k' down", 0, 365);
    text("First Player to 10 points!", 0, 390);
    if (mousePressed) {
      play = true;
      vsound.play();
    }
}
class Ball {
  float x, y, xspeed, yspeed, rad, wdth, ht;
  int score1, score2, sc;
  PImage img;
  Ball(float x, float y) {
    this.x = x;
    this.y = y;
    xspeed = 3;
    yspeed = 4;
    score1 = 0;
    score2 = 0;
    rad = 20;
    img = loadImage("pingpongball.png");
    wdth = 20;
    ht = 20;
    sc = 0;
  }
  public void move() {
    sc =0;
    x += xspeed;
    y += yspeed;
    if (x >= width-wdth/2) {
      x = 200;
      y = 200;
      xspeed = -3;
      score2 = score2+1;
      bonus.y = 100;
      bonus2.y = 300;
      sc = 1;
    }
    if (x <= wdth/2){
      x = 200;
      y = 200;
      xspeed = 3;
      bonus.y = 100;
      bonus2.y = 300;
      score1 = score1+1;
      sc = 1;
      
    }
    if (y >= height-ht/2 || y <= ht/2) {
      yspeed *= -1;
      sc = 1;
    }
     
  }
   public void display() {
    image(img, x-25, y-25);
  }
   public float left(){
    return x-rad/2;
  }
  public float right(){
    return x+rad/2;
  }
  public float top(){
    return y-rad/2;
  }
  public float bottom(){
    return y+rad/2;
  }
}
class Bonus {
  int x, y, rad, yspeed;
  PImage img;

  Bonus(int x, int y) {
    rad = 30; 
    this.x = x;
    yspeed = 1;
    this.y = y;
    img = loadImage("rightboost.png");
  } 
  public void move() {
    y += yspeed;
    if (y >= height-ht/2 || y <= ht/2) {
      yspeed *= -1;
    }
     
  }
  public void display() {
   image(img, x-25, y-25);
  }
}
class Bonus2 {
  int x, y, rad, yspeed;
  PImage img;

  Bonus2(int x, int y) {
    rad = 30; 
    this.x = x;
    yspeed = 1;
    img = loadImage("leftboost.png");
    this.y = y;
  } 
  public void move() {
    y -= yspeed;
    if (y >= height-ht/2 || y <= ht/2) {
      yspeed *= -1;
    }
     
  }
  public void display() {
   image(img, x-25, y-25);
  }
}
class Paddle {
  float x, y, w, h, xspeed, yspeed;
  PImage img;
  Paddle(float x, float y) {
    this.x = x;
    this.y = y;
    xspeed = 0;
    img = loadImage("pingpongpaddle.png");
    yspeed = 0;
    w = 5;
    h = 100;  
  }
  public void move(){
    y += yspeed;
    x += xspeed;
  }
  public void display() {
    image(img, x-50, y-50);
    }
  public float left(){
    return x-w/2;
  }
  public float right(){
    return x+w/2;
  }
  public float top(){
    return y-h/2;
  }
  public float bottom(){
    return y+h/2;
  }
  }
  public void settings() {  size(400, 400); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "PINGPONG" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
