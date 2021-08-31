public class NetworkLibrary{

   Matrix statM = new Matrix(1, 1);
   
   static float e = 2.7182817f;
   float[] a;
   float[] outputs;
   float[] targets;
   XORData[] data;
   public static void main(String[] args){
   	  Network n;
      n = new Network(4, 8, 1, 4, 0.01f);
    //  XORData[] data = new XORData[]{
    //    new XORData(1, 1, 0f),
    //    new XORData(1, 0, 1f),
    //    new XORData(0, 1, 1f),
    //    new XORData(0, 0, 0f)
    //  };
     CheckerData[] data2 = new CheckerData[]{
      new CheckerData(0, 0, 0, 0, 0f),
      new CheckerData(1, 0, 0, 0, 0f),
      new CheckerData(0, 1, 0, 0, 0f),
      new CheckerData(1, 1, 0, 0, 0f),
      new CheckerData(0, 0, 1, 0, 0f),
      new CheckerData(1, 0, 1, 0, 0f),
      new CheckerData(0, 1, 1, 0, 1f),
      new CheckerData(1, 1, 1, 0, 0f),
      new CheckerData(0, 0, 0, 1, 0f),
      new CheckerData(1, 0, 0, 1, 1f),
      new CheckerData(0, 1, 0, 1, 0f),
      new CheckerData(1, 1, 0, 1, 0f),
      new CheckerData(0, 0, 1, 1, 0f),
      new CheckerData(1, 0, 1, 1, 0f),
      new CheckerData(0, 1, 1, 1, 0f),
      new CheckerData(1, 1, 1, 1, 0f),
    };

    for(int i = 0; i < 1000000; i++){
      int a = (int) Math.floor(Math.random() * data2.length);
      CheckerData d = data2[a];
      n.train(d.inputs, d.ans);
      System.out.println(n.guess(data2[6].inputs)[0]);
      if(Math.abs(n.error) < 0.1){
        break;
      }
      //n.train(new float[]{1}, new float[]{0});
     }

    //  for(int i = 0; i < 1000000; i++){
    //    int a = (int) Math.floor(Math.random() * 4);
    //    XORData d = data[a];
    //    n.train(d.inputs, d.ans);
    //    if(Math.abs(n.error) < 0.001){
    //      break;
    //    }
    //    //n.train(new float[]{1}, new float[]{0});
    //   }
     
     n.print();
     for(int i = 0; i < 16; i++){
       System.out.println(n.guess(data2[i].inputs)[0]);
     }
    //  System.out.println(n.guess(new float[]{1, 1})[0]);
    //  System.out.println(n.guess(new float[]{1, 0})[0]);
    //  System.out.println(n.guess(new float[]{0, 1})[0]);
    //  System.out.println(n.guess(new float[]{0, 0})[0]);
   }
}