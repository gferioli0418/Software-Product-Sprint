var s;
var scl = 20;
var food;
var up = true;
var down = true;
var left = true;
var right = true;

function setup() {
  createCanvas(600, 600);
  s = new Snake();
  foodLocation();
}

function foodLocation() {
  var columns = floor(width / scl);
  var rows = floor(height / scl);
  food = createVector(floor(random(columns)), floor(random(rows)));
  food.mult(scl);
}

function draw() {
  background(51);
  s.destroy();
  s.show();
  s.move();
  if (s.eat(food)) {
    foodLocation();
  }
  frameRate(10);
  fill(153, 42, 99);
  rect(food.x, food.y, scl, scl);
}

function keyPressed() {
  if (keyCode === UP_ARROW && up) {
    s.direction(0, -1);
    up = true;
    down = false;
    left = true;
    right = true;
  } else if (keyCode === DOWN_ARROW && down) {
    s.direction(0, 1);
    up = false;
    down = true;
    left = true;
    right = true;
  } else if (keyCode === RIGHT_ARROW && right) {
    s.direction(1, 0);
    up = true;
    down = true;
    left = false;
    right = true;
  } else if (keyCode === LEFT_ARROW && left) {
    s.direction(-1, 0);
    up = true;
    down = true;
    left = true;
    right = false;
  }
}
