// package com.example.NEAT.Utils;

// import java.util.HashMap;
// import java.util.HashSet;

// import com.example.NEAT.Network.Genes.ConnectionGene;
// import com.example.NEAT.Network.Genes.Genome;
// import com.example.NEAT.Network.Structures.Node;

// import processing.core.PApplet;
// import processing.core.PConstants;

// public class NetworkDisplay {
//     HashMap<Integer, NodeDisplayObject> nodeMap;
//     HashSet<ConnectionDisplayObject> connectionSet;
//     Genome displayGenome;
//     boolean showText = true;
//     public float height;
//     public float width;
//     public int tallestLayer;

//     private class NodeDisplayObject {
//         public Integer innovationNum;
//         public Float x;
//         public Float y;
//         public Double value;

//         public NodeDisplayObject(int i, float f1, float f2, double d) {
//             innovationNum = i;
//             x = f1;
//             y = f2;
//             value = d;
//         }
//     }

//     private class ConnectionDisplayObject {
//         public int innovationNum;
//         public int inNode;
//         public int outNode;
//         public Double value;
//         public boolean enabled;

//         public ConnectionDisplayObject(int innov, int in, int out, double d, boolean e) {
//             innovationNum = innov;
//             inNode = in;
//             outNode = out;
//             value = d;
//             enabled = e;
//         }
//     }

//     public NetworkDisplay(float w, float h) {
//         width = w;
//         height = h;
//     }

//     public void display(PApplet p, float x, float y) {
//         if (displayGenome != null) {
//             p.textAlign(PConstants.CENTER, PConstants.CENTER);
//             p.textSize(height / (tallestLayer * 6));
//             p.pushMatrix();
//             p.translate(x, y);
//             for (ConnectionDisplayObject cdo : connectionSet) {
//                 cdo.value = displayGenome.connections.get(cdo.innovationNum).weight;
//                 p.strokeWeight((float) (Math.abs(cdo.value) * (height / 200)));
//                 if (cdo.enabled) {
//                     if (cdo.value > 0) {
//                         NodeDisplayObject in = nodeMap.get(cdo.inNode);
//                         NodeDisplayObject out = nodeMap.get(cdo.outNode);
//                         p.stroke(0, 0, 255);
//                         p.line(in.x, in.y, out.x, out.y);

//                     } else {
//                         NodeDisplayObject in = nodeMap.get(cdo.inNode);
//                         NodeDisplayObject out = nodeMap.get(cdo.outNode);
//                         p.stroke(255, 0, 0);
//                         p.line(in.x, in.y, out.x, out.y);
//                     }
//                 } else {
//                     NodeDisplayObject in = nodeMap.get(cdo.inNode);
//                     NodeDisplayObject out = nodeMap.get(cdo.outNode);
//                     p.stroke(127.5f);
//                     p.line(in.x, in.y, out.x, out.y);
//                 }
//             }
//             for (NodeDisplayObject nd : nodeMap.values()) {
//                 nd.value = displayGenome.nodes.get(nd.innovationNum).inputSum;
//                 p.strokeWeight(2);
//                 p.stroke(0);
//                 p.fill(255 * (float) ((nd.value + 1) / 2));
//                 p.ellipse(nd.x, nd.y, height / (tallestLayer * 2), height / (tallestLayer * 2));
//                 if (showText) {
//                     if (nd.value > 0) {
//                         p.fill(0);
//                     } else {
//                         p.fill(255);
//                     }
//                     // System.out.println(nd.value);
//                     if((nd.value + "").length() > 4){
//                         p.text((nd.value + "").substring(0, 4), nd.x, nd.y);
//                     } else{
//                         p.text(nd.value + "", nd.x, nd.y);
//                     }
//                 }
//             }
//             p.popMatrix();
//         }
//     }

//     public void updateGenome(Genome g) {
//         if (displayGenome != g) {
//             displayGenome = g;
//             nodeMap = new HashMap<>();
//             g.generateNetwork();
//             for (Integer i : g.network.keySet()) {
//                 int layerHeight = g.network.get(i).size();
//                 if (layerHeight > tallestLayer) {
//                     tallestLayer = layerHeight;
//                 }
//                 float count = 0;
//                 for (Node n : g.network.get(i)) {
//                     nodeMap.put(n.idNum,
//                             new NodeDisplayObject(n.idNum, width * (float) i / g.network.size(),
//                                     height * ((count + 0.5f) / layerHeight), n.inputSum));
//                     count++;
//                 }
//             }
//             connectionSet = new HashSet<>();
//             for (Integer i : g.connections.keySet()) {
//                 ConnectionGene cg = g.connections.get(i);
//                 connectionSet.add(new ConnectionDisplayObject(i, cg.in.idNum, cg.out.idNum, cg.weight, cg.enabled));
//             }
//         }
//     }
// }
