using System;
using System.Collections;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Threading;
using ClarionApp;
using ClarionApp.Exceptions;
using ClarionApp.Model;
using Gtk;

namespace ClarionApp {
    class MainClass {
        #region properties
        private WSProxy ws = null;
        private ClarionAgent agent;
        String creatureId = String.Empty;
        String creatureName = String.Empty;
        int spawnPoint = 100;
        int deliverySpotPoint = -80;
        #endregion

        #region constructor
        public MainClass () {
            Application.Init ();
            Console.WriteLine ("ClarionApp V0.8");
            try {
                ws = new WSProxy ("localhost", 4011);

                String message = ws.Connect ();

                if (ws != null && ws.IsConnected) {
                    Console.Out.WriteLine ("[SUCCESS] " + message + "\n");
                    setUpWorld (ws);
                    ws.NewDeliverySpot (Thing.CATEGORY_DeliverySPOT, deliverySpotPoint, deliverySpotPoint);
                    ws.NewCreature (spawnPoint, spawnPoint, 0, out creatureId, out creatureName);
                    ws.SendCreateLeaflet ();

                    if (!String.IsNullOrWhiteSpace (creatureId)) {
                        ws.SendStartCamera (creatureId);
                        ws.SendStartCreature (creatureId);
                    }

                    Console.Out.WriteLine ("Creature created with name: " + creatureId + "\n");
                    agent = new ClarionAgent (ws, creatureId, creatureName);
                    agent.Run ();
                    Console.Out.WriteLine ("Running Simulation ...\n");
                } else {
                    Console.Out.WriteLine ("The WorldServer3D engine was not found ! You must start WorldServer3D before running this application !");
                    System.Environment.Exit (1);
                }
            } catch (WorldServerInvalidArgument invalidArtgument) {
                Console.Out.WriteLine (String.Format ("[ERROR] Invalid Argument: {0}\n", invalidArtgument.Message));
            } catch (WorldServerConnectionError serverError) {
                Console.Out.WriteLine (String.Format ("[ERROR] Is is not possible to connect to server: {0}\n", serverError.Message));
            } catch (Exception ex) {
                Console.Out.WriteLine (String.Format ("[ERROR] Unknown Error: {0}\n", ex.Message));
            }
            Application.Run ();
        }
        #endregion

        #region Methods
        public static void Main (string[] args) {
            new MainClass ();
        }

        public static void setUpWorld (WSProxy ws) {
            ws.SendWorldReset ();
            //ws.NewBrick (4, 747, 2, 800, 567);
            //ws.NewBrick (4, 50, -4, 747, 47);
            //ws.NewBrick (4, 49, 562, 796, 599);
            //ws.NewBrick (4, -2, 6, 50, 599);

            Random random = new Random ();
            int x = 0;
            int y = 0;

            for (int nJewel = 0; nJewel < 3; nJewel++) {
                for (int jewelType = 0; jewelType < 6; jewelType++) {
                    x = random.Next (30, 800);
                    y = random.Next (30, 600);
                    ws.NewJewel (jewelType, x, y);
                }
            }
        }
        #endregion
    }

}