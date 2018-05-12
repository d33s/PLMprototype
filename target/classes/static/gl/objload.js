var camera, scene, renderer;
var mouseX = 0, mouseY = 0;
var windowHalfX = window.innerWidth / 2;
var windowHalfY = window.innerHeight / 2;

camera = new THREE.PerspectiveCamera(45, window.innerWidth / window.innerHeight, 0.1, 2000);
//camera = new THREE.OrthographicCamera( window.innerWidth / - 100, window.innerWidth / 100, window.innerHeight / 100, window.innerHeight / - 100, 1, 2000 );
camera.position.set(0, 2, -10);

// scene
scene = new THREE.Scene();
var ambientLight = new THREE.AmbientLight(0xcccccc, 0.4);
scene.add(ambientLight);

var color = new THREE.Color(0xffffff);
scene.background = color;

var pointLight = new THREE.PointLight(0xffffff, 0.8);
camera.add(pointLight);

//camera.lookAt(scene.position);
scene.add(camera);

var manager = new THREE.LoadingManager();
manager.onProgress = function (item, loaded, total) {
    console.log(item, loaded, total);
};

var textureLoader = new THREE.TextureLoader(manager);
var texture = textureLoader.load('../img/black.jpg');

var onProgress = function (xhr) {
    if (xhr.lengthComputable) {
        var percentComplete = xhr.loaded / xhr.total * 100;
        console.log(Math.round(percentComplete, 2) + '% downloaded');
    }
};

var onError = function (xhr) {
};

console.log(window.opener.file3DPath+"<=======");

var loader = new THREE.OBJLoader(manager);
loader.load(window.opener.file3DPath, function (object) {
    object.traverse(function (child) {
        if (child instanceof THREE.Mesh) {
            child.material.map = texture;
        }
    });
    object.position.y = 0;
    scene.add(object);
}, onProgress, onError);

renderer = new THREE.WebGLRenderer({canvas: myCanvas});
renderer.setPixelRatio(window.devicePixelRatio);
//renderer.setSize(window.innerWidth / 2, window.innerHeight / 2);
renderer.setSize(window.innerWidth, window.innerHeight);
document.body.appendChild(renderer.domElement);

document.addEventListener('mousemove', onDocumentMouseMove, false);
window.addEventListener('resize', onWindowResize, false);
window.addEventListener('mousedown', onDocumentMouseClickDWN, false);
window.addEventListener('mouseup', onDocMouseUP, false);


//orbit controls - for rotating cube
var controls = new THREE.OrbitControls(camera, renderer.domElement);
controls.enableDamping = true;
controls.dampingFactor = 0.25;
controls.enableZoom = true;
controls.update();

var isThisFistClick = true;
var mouseDown_1 = new THREE.Vector2();
var mouseDown_2 = new THREE.Vector2();


function onDocumentMouseClickDWN(event) {
    if (event.which === 2) {
        if (isThisFistClick === true) {
            mouseDown_1.x = (event.clientX / window.innerWidth) * 2 - 1;
            mouseDown_1.y = -((event.clientY + document.body.scrollTop) / window.innerHeight) * 2 + 1;
            isThisFistClick = false;
        }
        else if (isThisFistClick === false) {
            mouseDown_2.x = (event.clientX / window.innerWidth) * 2 - 1;
            mouseDown_2.y = -((event.clientY + document.body.scrollTop) / window.innerHeight) * 2 + 1;
            isThisFistClick = true;
        }
    }
}

function onDocMouseUP(event) {
    if (event.which === 2 && mouseDown_1.equals(mouseDown_2)) { // jak pierwszy i drugi klik w jednym miejscu
        controls.reset();
        controls.update();
    }
}

function onWindowResize() {
    windowHalfX = window.innerWidth / 2;
    windowHalfY = window.innerHeight / 2;
    camera.aspect = window.innerWidth / window.innerHeight;
    camera.updateProjectionMatrix();
    renderer.setSize(window.innerWidth, window.innerHeight);
}

function onDocumentMouseMove(event) {
    mouseX = ( event.clientX - windowHalfX ) / 2;
    mouseY = ( event.clientY - windowHalfY ) / 2;
}

function animate() {
    requestAnimationFrame(animate);
    render();
}

function render() {
    renderer.render(scene, camera);
}
