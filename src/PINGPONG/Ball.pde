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
  void move() {
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
   void display() {
    image(img, x-25, y-25);
  }
   float left(){
    return x-rad/2;
  }
  float right(){
    return x+rad/2;
  }
  float top(){
    return y-rad/2;
  }
  float bottom(){
    return y+rad/2;
  }
}
