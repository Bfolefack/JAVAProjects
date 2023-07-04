package com.example;


public class Grid {
    WarpedGlobeExperiments app;
    int[][] cells;
    int gridWidth, gridHeight;
    float xPos, yPos;
    float[] div2 = {1};

    Grid(float x_, float y_, int gw_, int gh_, WarpedGlobeExperiments app_) {
        cells = new int[gw_][gh_];
        gridWidth = gw_;
        gridHeight = gh_;
        xPos = x_;
        yPos = y_;
        app = app_;
        //println(app.seed);
        //println(1 + (abs(app.seed) % 5)/10.f);
        float smallest = Integer.MAX_VALUE; 
        float biggest = Integer.MIN_VALUE;
        float land_modifier = 1 + ((app.seed % 4)-1)/10.f;
        float land_modifier_power = (float)Math.pow(1000, land_modifier-1);
        System.out.println(land_modifier);
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                //System.out.println(Math.pow((getNoise(i, j, 1.5f) + 1000)/2, land_modifier) + "," + Math.pow(1000, land_modifier-1));
                int n = 
                    Math.pow((getNoise(i, j, 1.3f) + 1000)/2, land_modifier)/land_modifier_power > 500? 1000 : 0;
                //System.out.println(n);
                cells[i][j] += n;
            }
        }
        
        //Make Mountains
        OpenSimplexNoise mountainNoise = new OpenSimplexNoise(app.seed/154);
        OpenSimplexNoise foothillNoise = new OpenSimplexNoise(app.seed/435);
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) { 
                float n1 = (Math.abs(getNoise(i, j, 0.8f, mountainNoise))/1000.f);
                float n2 = (float) (Math.pow(((1-Math.abs(getNoise(i, j, 1.5f, foothillNoise))/1000.f)*(1-Math.abs(getNoise(i, j, 1.65f, foothillNoise))/1000.f)), 1.75)/(1.66666f) + 0.275f);
            
                float m1 = n1 < 0.156 ? (1-n1) * (1-n1)* (1-n1): 0.6f; 
                float m2 = n2 > 0.6f ? n2 : 0.6f;
                cells[i][j] =    (int) (cells[i][j] * (m2 > m1 ? m2 : m1));
            }
        }

        //Blur the map     
        blurMap((int)(gridWidth * .015f),(int)(gridWidth * .005), 3);
        blurMap(2, 1, 1);
        //Add other octaves of noise
        addOctaves();

        //Normalize the map
        normalize_map();
        display();
        getImage();
        System.out.println("done!");
    }

    private void addOctaves() {
        for(int k = 1; k <= 8; k++){
            for (int i = 0; i < gridWidth; i++) {
                for (int j = 0; j < gridHeight; j++) {
                    //System.out.println(cells[i][j]);
                    cells[i][j] += ((getNoise(i, j, (float) (Math.pow(2, k))) +500)*10/15)/((int)Math.pow(2, k));
                }
            }
        }
    }

    private void normalize_map() {
        float biggest = Integer.MIN_VALUE;
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                if (cells[i][j] > biggest)
                    biggest = cells[i][j];
            }
        }

        biggest/=1000;
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                //start2 + (stop2 - start2) * ((value - start1) / (stop1 - start1))     
                //cells[i][j] = (float) (0.5 + (1 - 0.5) * ((cells[i][j] - 0.5) / (biggest - 0.5)));
                cells[i][j]/=biggest;
            }
        }
    }



    private void blurMap(int default_blur_radius, int blur_gap, int blur_iterations) {
        for(int m = 0; m < blur_iterations; m++){
            int[][] tempCells = new int[gridWidth][gridHeight];
            for (int j = 0; j < gridHeight; j++) {
                float stretch = (1 - (Math.abs(j-0.5f*gridHeight)/(0.5f*gridHeight))) + 0.00001f;
                for (int i = 0; i < gridWidth; i++) {
                    int sum = 0;
                    int latitude_blur_radius = (int)(default_blur_radius * (1000-cells[i][j])/1000.f) + 1;
                    //int latitude_blur_radius = (default_blur_radius);
                    int longitude_blur_radius = (int) (latitude_blur_radius/stretch) + 1;
                    int count = 0;
                    if(longitude_blur_radius > gridWidth/2) longitude_blur_radius = gridWidth/2 - 1;
                        for (int k = -longitude_blur_radius + (m%blur_gap); k <= longitude_blur_radius; k += (longitude_blur_radius/latitude_blur_radius + 1)) {
                            for (int l = -latitude_blur_radius + (m%blur_gap); l <= latitude_blur_radius; l += blur_gap) {
                                int x = i+k;
                                int y = j+l;
                                if (x < 0) x = gridWidth + x;
                                if (y < 0) y= j;
                                if (x >= gridWidth) x = x - gridWidth;
                                if (y >= gridHeight) y=j;
                                sum += cells[x][y];
                                count++;
                            }
                        }
                    if(count == 0)
                        tempCells[i][j] = cells[i][j];
                    else
                        tempCells[i][j] = sum/(count);
                }
            }
            cells = tempCells;
        }
    }

    void getImage() {
        app.save("data/Map_" + app.seed + "_" + gridWidth + "x" + gridHeight + "_"+".png");
    }

    int getNoise(int xPos, int yPos, float r) {
        double lon = (((double)xPos/gridWidth) * Math.PI * 2);
        double lat = (((double)yPos/gridHeight) * Math.PI);

        double x = (r*Math.sin(lat)*Math.cos(lon));
        double y = (r*Math.sin(lat)*Math.sin(lon));
        double z = (r*Math.cos(lat));
        double n = (app.noise.eval(x, y, z));
        return    (int)(n * 1000);
    }
    
int getNoise(int xPos, int yPos, float r, OpenSimplexNoise noise) {
    double lon =    (((double)xPos/gridWidth) * Math.PI * 2);
    double lat =    (((double)yPos/gridHeight) * Math.PI);

    double x =    (r*Math.sin(lat)*Math.cos(lon));
    double y =    (r*Math.sin(lat)*Math.sin(lon));
    double z =    (r*Math.cos(lat));
    double n = (noise.eval(x, y, z));
    return    (int)(n * 1000);
    }


    public void display() {

        app.loadPixels();

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {


                app.pixels[j * gridWidth + i] = getColor(cells[i][j]);
            }
        }
        app.updatePixels();

        app.updatePixels();
    }

    int getColor(float noise) {
        //System.out.println(noise);
        noise = noise/1000.f;
        if (noise < .4) {
            return app.color(25, 40, 220);
        } else if (noise < .5) {
            return app.color(45, 85, 220);
        } else if (noise < .55) {
            return app.color(200, 230, 100);
        } else if (noise < .7) {
            return app.color(20, 225, 45);
        } else if (noise < .8) {
            return app.color(20, 150, 45);
        } else if (noise < .9) {
            return app.color(100, 50, 0);
        } else if (noise < .95) {
            return app.color(50, 25, 0);
        } else if (noise <= 1) {
            return app.color(255);
        } else {
        return app.color(noise * 255);
        }
    }

    // void graphGeodesic(int x, int y, float theta){
    //     float[] mapPointA = {(float) ((x - (float)gridWidth/2) * 2 * Math.PI / gridWidth),
    //                       (float) ((y - (float)gridHeight/2) * Math.PI / gridHeight)};

    //     float[] mapPointB = {(float) ((x - (float)gridWidth/2) * 2 * Math.PI / gridWidth + Math.cos(theta)*20),
    //                       (float) ((y - (float)gridHeight/2) * Math.PI / gridHeight + Math.sin(theta)*20)};
        
    //     float[] mapPointC = {(float) ((x - (float)gridWidth/2) * 2 * Math.PI / gridWidth + Math.cos(theta) * 40),
    //                       (float) ((y - (float)gridHeight/2) * Math.PI / gridHeight + Math.sin(theta) * 40)};
        
    //     // (r*Math.sin(lat)*Math.cos(lon));
    //     // (r*Math.sin(lat)*Math.sin(lon));
    //     // (r*Math.cos(lat));

    //     float[] pointA3D = {(float) (Math.sin(mapPointA[1])*Math.cos(mapPointA[0])),
    //                         (float) (Math.sin(mapPointA[1])*Math.sin(mapPointA[0])),
    //                         (float)  Math.cos(mapPointA[1])};

    //     float[] pointB3D = {(float) (Math.sin(mapPointB[1])*Math.cos(mapPointB[0])),
    //                         (float) (Math.sin(mapPointB[1])*Math.sin(mapPointB[0])),
    //                         (float)  Math.cos(mapPointB[1])};
        
    //     float[] pointC3D = {(float) (Math.sin(mapPointC[1])*Math.cos(mapPointC[0])),
    //                         (float) (Math.sin(mapPointC[1])*Math.sin(mapPointC[0])),
    //                         (float)  Math.cos(mapPointC[1])};

    //     float[] AB = {pointB3D[0] - pointA3D[0], pointB3D[1] - pointA3D[1], pointB3D[2] - pointA3D[2]};
    //     float[] AC = {pointC3D[0] - pointA3D[0], pointC3D[1] - pointA3D[1], pointC3D[2] - pointA3D[2]};

    //     float[] cross = {AB[1]*AC[2] - AB[2]*AC[1], AB[2]*AC[0] - AB[0]*AC[2], AB[0]*AC[1] - AB[1]*AC[0]};
    //     float d = (float) cross[0]*pointA3D[0] + cross[1]*pointA3D[1] + cross[2]*pointA3D[2];
        
    //     float[] plane = {cross[0], cross[1], cross[2], d};

    //     for(int i = 0; i < gridWidth; i++){
    //         for(int j = 0; j < gridHeight; j++){
    //             float lon = (float) (((i - (float)gridWidth/2) * 2 * Math.PI / gridWidth));
    //             float lat = (float) (((j - (float)gridHeight/2) * Math.PI / gridHeight));
    //             float dist = (float) (d - (plane[0] * Math.sin(lat) * Math.cos(lon) + plane[1] * Math.sin(lat) * Math.sin(lon) + plane[2] * Math.cos(lat)));
    //             if(Math.abs(dist) < 0.01){
    //                 app.pixels[j * gridWidth + i] = app.color(255, 0, 0);
    //             }
    //         }
    //     }

    //     float start_lon2 = (float) (((x - (float)gridWidth/2) * 2 * Math.PI / gridWidth) + Math.cos(theta) * 10);
    //     float start_lat2 = (float) (((y - (float)gridHeight/2) * Math.PI / gridHeight) + Math.sin(theta) * 10);
    //     float start_lon3 = (float) (((x - (float)gridWidth/2) * 2 * Math.PI / gridWidth) + Math.cos(theta) * 20);
    //     float start_lat3 = (float) (((y - (float)gridHeight/2) * Math.PI / gridHeight) + Math.sin(theta) * 20);


    //     // float[] pos1 = {(float) (Math.cos(start_lon1)*Math.cos(start_lat1)),
    //     //                                 (float) (Math.sin(start_lon1)*Math.cos(start_lat1)),
    //     //                                 (float) Math.sin(start_lat1)};

    //     // float[] pos2 = {(float) (Math.cos(start_lon2)*Math.cos(start_lat2)),
    //     //                                 (float) (Math.sin(start_lon2)*Math.cos(start_lat2)),
    //     //                                 (float) Math.sin(start_lat2)};

    //     // float[] pos3 = {(float) (Math.cos(start_lon3)*Math.cos(start_lat3)),
    //     //                                 (float) (Math.sin(start_lon3)*Math.cos(start_lat3)),
    //     //                                 (float) Math.sin(start_lat3)};

    //     // //cross product
    //     // float[] cross = {pos1[1]*pos2[2] - pos1[2]*pos2[1],
    //     //                                  pos1[2]*pos2[0] - pos1[0]*pos2[2],
    //     //                                  pos1[0]*pos2[1] - pos1[1]*pos2[0]};
            
    //         // float lat = (float) (Math.sin(lon))/4;
    //     app.updatePixels();
    // }


    void graphGeodesic(int x, int y, float theta){
        float start_lon1 = (float) ((x - (float)gridWidth/2) * 2 * Math.PI / gridWidth);
        float start_lat1 = (float) ((y - (float)gridHeight/2) * Math.PI / gridHeight);
        float start_lon2 = (float) (((x - (float)gridWidth/2) * 2 * Math.PI / gridWidth) + Math.cos(start_lat1)/2);
        float start_lat2 = (float) (((y - (float)gridHeight/2) * Math.PI / gridHeight) + Math.sin(start_lat1)/2);
        
        float[] pos1 = {(float) (Math.cos(start_lon1)*Math.cos(start_lat1)),
                                        (float) (Math.sin(start_lon1)*Math.cos(start_lat1)),
                                        (float) Math.sin(start_lat1)};

        float[] pos2 = {(float) (Math.cos(start_lon2)*Math.cos(start_lat2)),
                                        (float) (Math.sin(start_lon2)*Math.cos(start_lat2)),
                                        (float) Math.sin(start_lat2)};

        //cross product
        float[] cross = {pos1[1]*pos2[2] - pos1[2]*pos2[1],
                                           pos1[2]*pos2[0] - pos1[0]*pos2[2],
                                           pos1[0]*pos2[1] - pos1[1]*pos2[0]};
            
            // float lat = (float) (Math.sin(lon))/4;
        float a = cross[0];
        float b = cross[1];
        float c = cross[2];
        for(int i = 0; i < gridWidth; i++){
            float lon = (float) ((i - (float)gridWidth/2) * 2 * Math.PI / gridWidth);
            float lat = (float) Math.atan(-(a * Math.cos(lon) +b * Math.sin(lon))/c);
            int j = (int) (gridHeight/2 + lat * gridHeight/Math.PI);
            if(j >= 0 && j < gridHeight && i >= 0 && i < gridWidth)
                app.pixels[j * gridWidth + i] = app.color(255, 0, 0);
        }
        app.updatePixels();
    }

    

    void update() {
        display();
        graphGeodesic(app.mouseX, app.mouseY, (float) (Math.PI/1.9f));
    
    //     int i = app.mouseX;
    //     int j = app.mouseY;
    //     int default_blur_radius=10;
    //     float stretch = (1 - (Math.abs(j-0.5f*gridHeight)/(0.5f*gridHeight))) + 0.00001f;
    //                 int sum = 0;
    //                 int latitude_blur_radius = default_blur_radius+ 1;
    //                 //int latitude_blur_radius = (default_blur_radius);
    //                 int longitude_blur_radius = (int) (latitude_blur_radius/stretch) + 1;
    //                 int count = 0;
    //                 if(longitude_blur_radius > gridWidth/2) longitude_blur_radius = gridWidth/2 - 1;
    //                     for (int k = -longitude_blur_radius; k <= longitude_blur_radius; k += (2*longitude_blur_radius/latitude_blur_radius + 1)) {
    //                         for (int l = -latitude_blur_radius; l <= latitude_blur_radius; l++) {
    //                             int x = i+k;
    //                             int y = j+l;
    //                             if (x < 0) x = gridWidth + x;
    //                             if (y < 0) y= j;
    //                             if (x >= gridWidth) x = x - gridWidth;
    //                             if (y >= gridHeight) y=j;
    //             app.pixels[y * gridWidth + x] = app.color(255, 0, 0);
    //         }
    //     }
    //     app.updatePixels();


        
    //     //for (int i = 0; i < gridHeight; i++) {
    //     //    for (int j = 0; j < gridWidth; j++) {     
    //     //        Cells[i][j].update();
    //     //    }
    //     //}
    }
}
