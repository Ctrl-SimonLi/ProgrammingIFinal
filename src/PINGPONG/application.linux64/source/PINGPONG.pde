import processing.sound.*;
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
void setup() {
  size(400, 400);
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
void draw() {
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
void keyPressed() {
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
void keyReleased() {
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
void startScreen() {
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
