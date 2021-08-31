public class NetworkLibrary{

   Matrix statM = new Matrix(1, 1);
   
   static float e = 2.7182817f;
   float[] a;
   float[] outputs;
   float[] targets;
   XORData[] data;
   public static void main(String[] args){
   	  Network n;
      n = new Network(2, 8, 1);
     XORData[] data = new XORData[]{
       new XORData(1, 1, 1f),
       new XORData(1, 0, 0f),
       new XORData(0, 1, 0f),
       new XORData(0, 0, 1f)
     };
     for(int i = 0; i < 100000; i++){
       int a = (int) Math.floor(Math.random() * 4);
       XORData d = data[a];
       System.out.println(n.train(d.inputs, d.ans)[0]);
       //n.train(new float[]{1}, new float[]{0});
   }
     
     n.print();
     
     System.out.println(n.guess(new float[]{1, 1})[0]);
     System.out.println(n.guess(new float[]{1, 0})[0]);
     System.out.println(n.guess(new float[]{0, 1})[0]);
     System.out.println(n.guess(new float[]{0, 0})[0]);
   }
}