package renderer;

import static java.awt.Color.*;

import org.junit.jupiter.api.Test;

import lighting.*;
import geometries.*;
import primitives.*;
import scene.Scene;

public class OurPicture2Test {

    @Test
    public void ourPicture2() {
        Scene scene = new Scene("Test scene")//
                .setBackground(new Color(51, 153, 255));

        //Scene scene = new Scene.SceneBuilder("Test scene").build();//
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(1, 0, 0)) //
                .setVPDistance(600).setVPSize(250, 250); //

        Point S1 = new Point(-165, 170, -500);
        Point S2 = new Point(-165, -150, -500);
        Point S3 = new Point(-165, -150, -100);
        Point S4 = new Point(-165, 170, -100);
        Point SP1 = new Point(-145, 135, -120);
        Point SP2 = new Point(-145, 120, -180);
        Point SP3 = new Point(-145, 105, -240);
        Point be1 = new Point(150, 170, -500);
        Point be2 = new Point(150, -150, -500);
        Point light = new Point(300, -70, 0);
        Point h1 = new Point(-165, 80, -220);
        Point h2 = new Point(-165, -20, -150);
        Point h3 = new Point(-50, 80, -220);
        Point h4 = new Point(-50, -20, -150);
        Point h5 = new Point(-165, -100, -270);
        Point h6 = new Point(-50, -100, -270);
        Point h7 = new Point(-165, 0, -340);
        Point h8 = new Point(-50, 0, -340);
        Point h9 = new Point(0, 30, -185);
        Point h10 = new Point(0, -50, -305);
        Point h24 = new Point(-90, -20, -150);
        Point h56 = new Point(-90, -100, -270);
        Point d1 = new Point(-165, 55, -202);
        Point d2 = new Point(-165, 5, -167.5);
        Point d3 = new Point(-95, 55, -202);
        Point d4 = new Point(-95, 5, -167.5);
        Point d5 = new Point(-50, 5, -167.5);
        Point d6 = new Point(-50, 55, -202);
        Point h13 = new Point(-90, 80, -220);
        Point c = new Point(-50, -36, -174);
        Point d = new Point(-50, -68, -222);
        Point e = new Point(-165, -36, -174);
        Point f = new Point(-165, -68, -222);
        Point g = new Point(-60, -20, -150);
        Point h = new Point(-60, -100, -270);
        Point be3 = new Point(150, -150, -100);
        Point be4 = new Point(150, 170, -100);
        Point DD = new Point(-135, 0, -240);
        Point l1 = new Point(-80, -50, -210);
        Point A1 = new Point(-50, 55, -202.5);
        Point A2 = new Point(-50, 60, -250);
        Point A3 = new Point(-50, 35, -232.5);
        Point A4 = new Point(30, 55, -202.5);
        Point A5 = new Point(30, 35, -232.5);
        Point A6 = new Point(30, 60, -250);
        Point A7 = new Point(30, 80, -220);
        Point w1 = new Point(-60, -36, -174);
        Point w2 = new Point(-60, -68, -222);
        Point w3 = new Point(-100, -36, -174);
        Point w4 = new Point(-100, -68, -222);
        Point R1 = new Point(-70, -120, -170);
        Point R2 = new Point(-165, -100, -150);
        Point R3 = new Point(-165, -140, -150);
        Point R4 = new Point(-165, -120, -190);
        Point R5 = new Point(-40, -110, -200);
        Point R6 = new Point(-80, -130, -140);

        scene.geometries.add(
                new Polygon(S1, S2, S3, S4).setEmission(new Color(GREEN)) //surface
                        .setMaterial(new Material().setkD(0.5).setkS(0.8).setkR(0.1).setnShininess(20)),
                new Sphere(20, SP1).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setkD(0.5).setkS(0.4).setkT(0).setkR(0).setnShininess(20)),
                new Sphere(20, (SP2)).setEmission(new Color(RED))
                        .setMaterial(new Material().setkD(0.5).setkS(0.4).setkT(0).setkR(0).setnShininess(20)),
                new Sphere(20, SP3).setEmission(new Color(51, 153, 255))
                        .setMaterial(new Material().setkD(0.5).setkS(0.4).setkT(0.1).setkR(0).setnShininess(20)),
                new Polygon(S2, S1, be1, be2).setEmission(new Color(10, 100, 255)) //background
                        .setMaterial(new Material().setkD(0.5).setkS(0.8).setkR(0).setnShininess(20)),
                new Polygon(h1, h3, h8, h7).setEmission(new Color(GRAY)) //
                        .setMaterial(new Material().setkD(0.95).setkS(0).setkT(0).setkR(0).setnShininess(20)),
                new Polygon(h5, h6, h8, h7).setEmission(new Color(GRAY)) //
                        .setMaterial(new Material().setkD(0.95).setkS(0).setkT(0).setkR(0).setnShininess(20)),
                new Polygon(h6, h4, h3, h8).setEmission(new Color(GRAY)) //
                        .setMaterial(new Material().setkD(0).setkS(0).setkT(0).setkR(0).setnShininess(0)),
                new Triangle(h4, h9, h3).setEmission(new Color(RED)) //
                        .setMaterial(new Material().setkD(0.95).setkS(0).setkR(0).setkT(0).setnShininess(20)),
                new Triangle(h6, h10, h8).setEmission(new Color(RED)) //
                        .setMaterial(new Material().setkD(0.95).setkS(0).setkR(0).setkT(0).setnShininess(20)),
                new Polygon(h4, h9, h10, h6).setEmission(new Color(RED)) //
                        .setMaterial(new Material().setkD(0.95).setkS(0).setkR(0).setkT(0).setnShininess(20)),
                new Polygon(h10, h9, h3, h8).setEmission(new Color(RED)) //
                        .setMaterial(new Material().setkD(0.95).setkS(0).setkR(0).setkT(0).setnShininess(20)),
                new Polygon(h2, h4, d5, d2).setEmission(new Color(GRAY)) //
                        .setMaterial(new Material().setkD(0.95).setkS(0).setkR(0).setkT(0).setnShininess(20)),
                new Polygon(h1, h3, d6, d1).setEmission(new Color(GRAY)) //
                        .setMaterial(new Material().setkD(0.95).setkS(0).setkR(0).setkT(0).setnShininess(20)),
                new Polygon(h4, h24, h13, h3).setEmission(new Color(GRAY)) //
                        .setMaterial(new Material().setkD(0.95).setkS(0).setkR(0).setkT(0).setnShininess(20)),
                new Polygon(h2, h4, c, e).setEmission(new Color(GRAY)) //
                        .setMaterial(new Material().setkD(0.95).setkS(0).setkR(0).setkT(0).setnShininess(20)),
                new Polygon(f, d, h6, h5).setEmission(new Color(GRAY)) //
                        .setMaterial(new Material().setkD(0.95).setkS(0).setkR(0).setkT(0).setnShininess(20)),
                new Polygon(h2, h24, h56, h5).setEmission(new Color(GRAY)) //
                        .setMaterial(new Material().setkD(0.95).setkS(0).setkR(0).setkT(0).setnShininess(20)),
                new Polygon(g, h4, h6, h).setEmission(new Color(GRAY)) //
                        .setMaterial(new Material().setkD(0.95).setkS(0).setkR(0).setkT(0).setnShininess(20)),
                new Sphere(5, l1).setEmission(new Color(YELLOW))
                        .setMaterial(new Material().setkD(0.5).setkS(0.4).setkT(0.1).setkR(0.2).setnShininess(20)),

                new Sphere(15, new Point(-135, 45, -160.75))
                        .setEmission(new Color(51, 0, 0)) //
                        .setMaterial(new Material().setkD(0.95).setkS(0).setkR(0).setkT(0).setnShininess(20)),
                new Sphere(15, DD)
                        .setEmission(new Color(51, 0, 0)) //
                        .setMaterial(new Material().setkD(0.95).setkS(0).setkR(0).setkT(0).setnShininess(20)),
                new Sphere(2, new Point(-130, 46, -147))
                        .setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setkD(0.95).setkS(0).setkR(0).setkT(0).setnShininess(20)),
                new Sphere(2, new Point(-132, 55, -150))
                        .setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setkD(0.95).setkS(0).setkR(0).setkT(0).setnShininess(20)),
                new Sphere(5, new Point(-137, 50, -148))
                        .setEmission(new Color(153, 102, 0)) //
                        .setMaterial(new Material().setkD(0.95).setkS(0).setkR(0).setkT(0).setnShininess(20)),
                new Polygon(A1, A3, A5, A4).setEmission(new Color(GRAY)) //
                        .setMaterial(new Material().setkD(0.95).setkS(0).setkR(0).setkT(0).setnShininess(20)),
                new Polygon(A1, A4, A7, h3).setEmission(new Color(GRAY)) //
                        .setMaterial(new Material().setkD(0.95).setkS(0).setkR(0).setkT(0).setnShininess(20)),
                new Polygon(h3, A7, A6, A2).setEmission(new Color(GRAY)) //
                        .setMaterial(new Material().setkD(0.95).setkS(0).setkR(0).setkT(0).setnShininess(20)),
                new Polygon(A2, A6, A5, A3).setEmission(new Color(GRAY)) //
                        .setMaterial(new Material().setkD(0.95).setkS(0).setkR(0).setkT(0).setnShininess(20)),
                new Polygon(A4, A5, A6, A7).setEmission(new Color(GRAY)) //
                        .setMaterial(new Material().setkD(0.95).setkS(0).setkR(0).setkT(0).setnShininess(20)),
                new Sphere(12.5, new Point(50, 57.5, -226.25)).setEmission(new Color(BLACK)) //
                        .setMaterial(new Material().setkD(0.05).setkS(0).setkR(0).setkT(0.85).setnShininess(20)),
                new Sphere(12.5, new Point(80, 57.5, -226.25)).setEmission(new Color(BLACK)) //
                        .setMaterial(new Material().setkD(0).setkS(0).setkR(0).setkT(0.96).setnShininess(20)),
                new Sphere(12.5, new Point(110, 57.5, -226.25)).setEmission(new Color(BLACK)) //
                        .setMaterial(new Material().setkD(0).setkS(0).setkR(0).setkT(0.98).setnShininess(20)),
                new Polygon(w1, w2, w4, w3).setEmission(new Color(BLACK)) //
                        .setMaterial(new Material().setkD(0).setkS(0).setkR(0).setkT(0.96).setnShininess(20)));


        scene.lights.add(
                new SpotLight(new Color(400, 240, 0), light, new Vector(-280, 65, -300)) //
                        .setkL(1E-5).setkQ(1.5E-7)
        );
        scene.lights.add(
                new SpotLight(new Color(400, 240, 0), new Point(-135, 0, -260), new Vector(-55, 50, 10)) //
                        .setkL(1E-5).setkQ(1.5E-7));


        ImageWriter imagewriter = new ImageWriter("ourPicture2", 1000, 1000);

        camera.setImageWriter(imagewriter)
                .setRayTracerBase(new RayTracerBasic(scene))
                .setNumberOfRays(300)
                .renderImage();
        camera.writeToImage();

    }

}