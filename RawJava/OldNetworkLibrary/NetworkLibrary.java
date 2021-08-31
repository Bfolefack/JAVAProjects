public class NetworkLibrary{

   Matrix statM = new Matrix(1, 1);
   
   static float e = 2.7182817f;
   float[] a;
   float[] outputs;
   float[] targets;
   XORData[] data;
   public static void main(String[] args){
   	  Network n;
      n = new Network(2, 2, 1, 2, 0.5f);
     XORData[] data = new XORData[]{
       new XORData(1, 1, 0),
       new XORData(1, 0, 1),
       new XORData(0, 1, 1),
       new XORData(0, 0, 0)
     };
     for(int i = 0; i < 1000000; i++){
       int a = (int) Math.floor(Math.random() * 4);
       XORData d = data[a];
       n.train(d.inputs, d.ans);
       //n.train(new float[]{1}, new float[]{0});
   }
     
     n.print();
     
     System.out.println(n.guess(new float[]{1, 1})[0]);
     System.out.println(n.guess(new float[]{1, 0})[0]);
     System.out.println(n.guess(new float[]{0, 1})[0]);
     System.out.println(n.guess(new float[]{0, 0})[0]);
   }
}