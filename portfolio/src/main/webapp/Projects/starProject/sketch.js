var circle = []

var speed;
var number = 1000;
function setup() {
  slider = createCanvas(windowWidth,windowHeight);
  for (i=0; i<number;i++){
    circle[i]=new star();

  }
  // createSlider(1,25,10);

}

function draw() {
speed = map(mouseX, 0, width, 0, 80);
  translate(width/2,height/2);
  background(0);
  for (i=0; i<number;i++){
    circle[i].show();
    circle[i].move();
  }

}
