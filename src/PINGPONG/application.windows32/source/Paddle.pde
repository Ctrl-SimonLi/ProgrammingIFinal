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
  void move(){
    y += yspeed;
    x += xspeed;
  }
  void display() {
    image(img, x-50, y-50);
    }
  float left(){
    return x-w/2;
  }
  float right(){
    return x+w/2;
  }
  float top(){
    return y-h/2;
  }
  float bottom(){
    return y+h/2;
  }
  }
