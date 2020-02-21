let bubbles = [];
let radius = 16;


function setup() {

  let Vg = createVector(0, .1);
  createCanvas(windowWidth, windowHeight );



}

function mousePressed() {
  let b = new Bubble(mouseX, mouseY, 0, 4, radius);
  bubbles.push(b);
}
function draw() {

  background(0, 77, 153);

  for (let i = 0; i < bubbles.length; i++) {
    bubbles[i].show();
    if (bubbles[i].fast) {
      bubbles[i].move();
    }
    else {
      if (bubbles[i].radius > 1) {
        bubbles[i].explode();
      }
      bubbles.splice(i, 1);
    }
  }
}

function Bubble(x, y, dx, dy, r) {

  this.x = x;
  this.y = y;
  this.dx = dx;
  this.dy = dy;
  this.fast = true;
  this.radius = r;




  this.move = function () {
    this.x = this.x + this.dx;
    this.y = this.y + this.dy;
    this.dy = this.dy + .1;
    if (this.x - this.radius <= 0 || this.x + this.radius >= width) {
      this.dx = -this.dx;
    }

    if (this.y >= height - this.radius) {

      this.dy = -.75 * this.dy;
      this.y = height - this.radius;

      if (abs(this.dy) < 1) {
        this.fast = false;
      }
    }
  }

  this.show = function () {

    stroke(255);
    strokeWeight(1);
    fill(148, 184, 184);
    ellipse(this.x, this.y, this.radius * 2);

  }

  this.explode = function () {
    for (let i = 0; i <= 5; i++) {
      let b = new Bubble(this.x, this.y, random(-6, 6), random(-6, 6), this.radius / 2);
      bubbles.push(b);
    }
  }
}
