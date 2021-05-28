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
  void move() {
    y -= yspeed;
    if (y >= height-ht/2 || y <= ht/2) {
      yspeed *= -1;
    }
     
  }
  void display() {
   image(img, x-25, y-25);
  }
}
