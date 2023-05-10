package scene;

import geometries.*;
import primitives.*;
import lighting.AmbientLight;
import lighting.LightSource;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.Scanner;

import static java.lang.Double.parseDouble;

/**
 * A class of scene that represent a scene(an image) with all the properties
 * like name, background, geometries ect
 */
public class Scene {
    public String name;
    public Color background;
    public AmbientLight ambientLight;
    public Geometries geometries;
    public List<LightSource> lights = new LinkedList<>();

    public Scene(String name) {
        this.name = name;
        this.background = Color.BLACK;
        this.ambientLight = AmbientLight.NONE;
        this.geometries = new Geometries();
    }

    /**
     * set the Background color of the scene
     *
     * @param background the color to set
     * @return this Scene
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * set the ambientLight of the scene
     *
     * @param ambientLight the AmbientLight to set
     * @return this Scene
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * set the geometries of the scene
     *
     * @param geometries the geometries to set
     * @return this Scene
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    /**
     * Sets the list of light sources for this scene.
     *
     * @param lights The list of light sources to set.
     * @return This Scene object with the updated list of light sources.
     */
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }


    /**
     * extract all the geometries from the XML file
     *
     * @param geometriesElement the Element that contains the geometries
     * @return the Geometries inside the Geometries Element
     */
    private Geometries GeometriesFromXmlElement(Element geometriesElement) {
        NodeList geoList = geometriesElement.getChildNodes();
        Geometries geometries = new Geometries();
        int length = geoList.getLength();
        Element IElement;
        Intersectable intersectable;
        for (int i = 0; i < length; ++i) {
            if (geoList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                IElement = (Element) geoList.item(i);
                switch (IElement.getTagName()) {
                    case "cylinder":
                        intersectable = new Cylinder(
                                parseDouble(IElement.getAttribute("radius")),
                                new Ray(new Point(doubl3Convert(IElement.getAttribute("p0"))),
                                        new Vector(doubl3Convert(IElement.getAttribute("direction")))),
                                parseDouble(IElement.getAttribute("height")));
                        break;
                    case "plane":
                        if (IElement.hasAttribute("normal")) {
                            intersectable = new Plane(
                                    new Point(doubl3Convert(IElement.getAttribute("p0"))),
                                    new Vector(doubl3Convert(IElement.getAttribute("normal")))
                            );
                        } else {
                            intersectable = new Plane(
                                    new Point(doubl3Convert(IElement.getAttribute("p0"))),
                                    new Point(doubl3Convert(IElement.getAttribute("p1"))),
                                    new Point(doubl3Convert(IElement.getAttribute("p2")))
                            );
                        }
                        break;
                    case "polygon":
                        String[] ver = IElement.getAttribute("vertices").split(", ");
                        ArrayList<Point> points = new ArrayList<>();

                        for (int j = 0; j < ver.length; j++) {
                            points.add(new Point(doubl3Convert(ver[j])));
                        }
                        intersectable = new Polygon(points.toArray(new Point[points.size()]));
                      break;
                    case "sphere":
                        intersectable = new Sphere(
                                parseDouble(IElement.getAttribute("radius")),
                                new Point(doubl3Convert(IElement.getAttribute("center")))
                        );
                        break;
                    case "triangle":
                        intersectable = new Triangle(
                                new Point(doubl3Convert(IElement.getAttribute("p0"))),
                                new Point(doubl3Convert(IElement.getAttribute("p1"))),
                                new Point(doubl3Convert(IElement.getAttribute("p2")))
                        );
                        break;


                    case "tube":
                        intersectable = new Tube(
                                parseDouble(IElement.getAttribute("radius")),
                                new Ray(new Point(doubl3Convert(IElement.getAttribute("point"))),
                                        new Vector(doubl3Convert(IElement.getAttribute("vector"))))
                        );
                        break;

                    default:
                        return null;
                }
                geometries.add(intersectable);
            }
        }
        return geometries;
    }

    /**
     * get a name of an XML file inside the current directory and build a scene
     *
     * @param imageName the name of the XML file
     * @return this Scene
     */
    public void setFromXML(String imageName) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(new File(System.getProperty("user.dir") + '/' + imageName));

            Element sceneElement = (Element) document.getElementsByTagName("scene").item(0);
            Element tagName = (Element) sceneElement.getElementsByTagName("ambient-light").item(0);

            this.background = new Color(doubl3Convert(sceneElement.getAttribute("background-color")));
            this.ambientLight = new AmbientLight(
                    new Color(doubl3Convert(tagName.getAttribute("color"))),
                    doubl3Convert(tagName.getAttribute("Ka")));

            tagName = (Element) sceneElement.getElementsByTagName("geometries").item(0);
            if (tagName.getChildNodes().getLength() == 0)//if there are no geometries in the scene
                return;
            this.geometries = GeometriesFromXmlElement(tagName);
            if (this.geometries == null)//same sing is wrong with the structure of the xml file
                throw new IllegalArgumentException("The xml file is not correctly structured");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Double3 doubl3Convert(String str) {
        Scanner stringScanner = new Scanner(str);
        return new Double3(parseDouble(stringScanner.next()),
                parseDouble(stringScanner.next()),
                parseDouble(stringScanner.next()));
    }
}
