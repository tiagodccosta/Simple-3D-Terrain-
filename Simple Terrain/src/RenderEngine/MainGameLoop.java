package RenderEngine;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import Entities.Camera;
import Entities.Entity;
import Entities.Light;
import Models.RawModel;
import Models.TexturedModel;
import Terrains.Terrain;
import Textures.ModelTexture;

public class MainGameLoop {

	public static void main(String []args) {
		
		   DisplayManager.createDisplay();
	        Loader loader = new Loader();
	         
	         
	        RawModel model = OBJLoader.loadObjModel("tree", loader);
	         
	        TexturedModel staticModel = new TexturedModel(model,new ModelTexture(loader.loadTexture("tree")));
	         
	        List<Entity> entities = new ArrayList<Entity>();
	        Random random = new Random();
	        for(int i=0;i<500;i++){
	            entities.add(new Entity(staticModel, new Vector3f(random.nextFloat()*800 - 400, 0, random.nextFloat() * -800),0,0,0,3));
	        }
	         
	        Light light = new Light(new Vector3f(20000,20000,2000),new Vector3f(1,1,1));
	         
	        Terrain terrain = new Terrain(0,-1,loader,new ModelTexture(loader.loadTexture("grass")));
	        Terrain terrain2 = new Terrain(-1,-1,loader,new ModelTexture(loader.loadTexture("grass")));
	         
	        Camera camera = new Camera();   
	        MasterRenderer renderer = new MasterRenderer();
	         
	        while(!Display.isCloseRequested()){
	            camera.move();
	             
	            renderer.processTerrain(terrain);
	            renderer.processTerrain(terrain2);
	            for(Entity entity:entities){
	                renderer.processEntity(entity);
	            }
	            renderer.render(light, camera);
	            DisplayManager.updateDisplay();
	        }
	 
	        renderer.cleanUp();
	        loader.cleanUp();
	        DisplayManager.closeDisplay();
	 
	    }
}
