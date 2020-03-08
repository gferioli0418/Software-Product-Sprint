// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 * Adds a random greeting to the page.
 */
function randomFact() {
  const Facts = [
    "I am slowly becoming vegan.",
    "I am bilingual(English and Spanish)",
    "I like do card tricks even though I am not so good at it.",
    "I try to grow a collection of rubber duckies for rubber duck debugging(search it up)",
    "I enjoy cooking",
    "I like learning new things"
  ];

  // Pick a random greeting.
  const Fact = Facts[Math.floor(Math.random() * Facts.length)];

  // Add it to the page.
  const factContainer = document.getElementById("fact-container");
  factContainer.innerText = Fact;
}

function getName() {
  fetch("/data")
    .then(response => response.json())
    .then(list => {
      console.log(list);
      const jsonHTML = document.getElementById("list-container");
      list.map(createListElement).forEach(e => jsonHTML.appendChild(e));
    });
}

function createListElement(text) {
  const liElement = document.createElement("li");
  liElement.innerText = text;
  return liElement;
}


function createMap() {
  const map = new google.maps.Map(document.getElementById("map"), {
    center: { lat: 29.646445, lng: -82.347676 },
    zoom: 16
  });
}

