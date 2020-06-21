using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Threading;
using Clarion;
using Clarion.Framework;
using Clarion.Framework.Core;
using Clarion.Framework.Templates;
using ClarionApp;
using ClarionApp.Model;
using Gtk;

namespace ClarionApp {
    /// <summary>
    /// Public enum that represents all possibilities of agent actions
    /// </summary>
    public enum CreatureActions {
        DO_NOTHING,
        WANDER,
        ROTATE_CLOCKWISE,
        GO_TO_DELIVERY_SPOT,
        GO_TO_DESIRED_JEWEL,
        GET_JEWEL,
        HIDE_JEWEL,
        EAT_FOOD,
        DELIVER_LEAFLET
    }

    public class ClarionAgent {
        #region Constants
        /// <summary>
        /// Constant that represents the Visual Sensor
        /// </summary>
        private String SENSOR_VISUAL_DIMENSION = "VisualSensor";
        /// <summary>
        /// Constant that represents that there is at least one wall ahead
        /// </summary>
        private String DIMENSION_NOTHING_DESIRED_AHEAD = "NothingDesiredAhead";
        private String DIMENSION_WALL_AHEAD = "WallAhead";
        private String DIMENSION_DESIRED_JEWEL_AWAY = "DesiredJewelAway";
        private String DIMENSION_DESIRED_JEWEL_AHEAD = "DesiredJewelAhead";
        private String DIMENSION_UNDESIRED_JEWEL_AHEAD = "UndesiredJewelAhead";
        private String DIMENSION_FOOD_AHEAD = "FoodAhead";
        private String DIMENSION_DELIVERY_SPOT_AHEAD = "DeliverySpotAhead";
        private String DIMENSION_DELIVER_LEAFLET = "DeliverLeaflet";
        double prad = 0;
        #endregion

        #region Properties
        public MindViewer mind;
        String creatureId = String.Empty;
        String creatureName = String.Empty;
        #region Simulation
        /// <summary>
        /// If this value is greater than zero, the agent will have a finite number of cognitive cycle. Otherwise, it will have infinite cycles.
        /// </summary>
        public double MaxNumberOfCognitiveCycles = -1;
        /// <summary>
        /// Current cognitive cycle number
        /// </summary>
        private double CurrentCognitiveCycle = 0;
        /// <summary>
        /// Time between cognitive cycle in miliseconds
        /// </summary>
        public Int32 TimeBetweenCognitiveCycles = 0;
        /// <summary>
        /// A thread Class that will handle the simulation process
        /// </summary>
        private Thread runThread;
        #endregion

        #region Agent
        private WSProxy worldServer;
        /// <summary>
        /// The agent 
        /// </summary>
        private Clarion.Framework.Agent CurrentAgent;
        #endregion

        #region Perception Input
        /// <summary>
        /// Perception input to indicates a wall ahead
        /// </summary>
        private DimensionValuePair inputNothingDesiredAhead;
        private DimensionValuePair inputWallAhead;
        private DimensionValuePair inputDesiredJewelAhead;
        private DimensionValuePair inputDesiredJewelAway;
        private DimensionValuePair inputUndesiredJewelAhead;
        private DimensionValuePair inputFoodAhead;
        private DimensionValuePair inputDeliverySpotAhead;
        private DimensionValuePair inputDeliverLeaflet;
        #endregion

        #region Action Output
        /// <summary>
        /// Output action that makes the agent to rotate clockwise
        /// </summary>
        private ExternalActionChunk outputRotateClockwise;
        /// <summary>
        /// Output action that makes the agent to Wander
        /// </summary>
        private ExternalActionChunk outputWander;
        /// <summary>
        /// Output action that makes the agent go to DeliverySpot
        /// </summary>
        private ExternalActionChunk outputGoToDeliverySpot;
        /// <summary>
        /// Output action that makes the agent Deliver Leaflet
        /// </summary>
        private ExternalActionChunk outputDeliverLeaflet;
        /// <summary>
        /// Output action that makes the agent go to desired jewel
        /// </summary>
        private ExternalActionChunk outputGoToDesiredJewel;
        /// <summary>
        /// Output action that makes agent get jewel
        /// </summary>
        private ExternalActionChunk outputGetJewel;
        /// <summary>
        /// Output action that makes agent hide jewel
        /// </summary>
        private ExternalActionChunk outputHideJewel;
        /// <summary>
        /// Output action that makes agent eat food
        /// </summary>
        private ExternalActionChunk outputEatFood;
        #endregion

        #endregion

        #region Other Variables
        CreatureActions lastAction = (CreatureActions) Enum.Parse (typeof (CreatureActions), "DO_NOTHING", true);

        #endregion

        #region Constructor
        public ClarionAgent (WSProxy nws, String creature_ID, String creature_Name) {
            worldServer = nws;
            // Initialize the agent
            CurrentAgent = World.NewAgent ("Current Agent");
            mind = new MindViewer ();
            mind.Show ();
            creatureId = creature_ID;
            creatureName = creature_Name;

            // Initialize Input Information
            inputNothingDesiredAhead = World.NewDimensionValuePair (SENSOR_VISUAL_DIMENSION, DIMENSION_NOTHING_DESIRED_AHEAD);
            inputWallAhead = World.NewDimensionValuePair (SENSOR_VISUAL_DIMENSION, DIMENSION_WALL_AHEAD);
            inputDesiredJewelAhead = World.NewDimensionValuePair (SENSOR_VISUAL_DIMENSION, DIMENSION_DESIRED_JEWEL_AHEAD);
            inputDesiredJewelAway = World.NewDimensionValuePair (SENSOR_VISUAL_DIMENSION, DIMENSION_DESIRED_JEWEL_AWAY);
            inputUndesiredJewelAhead = World.NewDimensionValuePair (SENSOR_VISUAL_DIMENSION, DIMENSION_UNDESIRED_JEWEL_AHEAD);
            inputFoodAhead = World.NewDimensionValuePair (SENSOR_VISUAL_DIMENSION, DIMENSION_FOOD_AHEAD);
            inputDeliverySpotAhead = World.NewDimensionValuePair (SENSOR_VISUAL_DIMENSION, DIMENSION_DELIVERY_SPOT_AHEAD);
            inputDeliverLeaflet = World.NewDimensionValuePair (SENSOR_VISUAL_DIMENSION, DIMENSION_DELIVER_LEAFLET);

            // Initialize Output actions
            outputRotateClockwise = World.NewExternalActionChunk (CreatureActions.ROTATE_CLOCKWISE.ToString ());
            outputWander = World.NewExternalActionChunk (CreatureActions.WANDER.ToString ());
            outputGoToDesiredJewel = World.NewExternalActionChunk (CreatureActions.GO_TO_DESIRED_JEWEL.ToString ());
            outputGetJewel = World.NewExternalActionChunk (CreatureActions.GET_JEWEL.ToString ());
            outputHideJewel = World.NewExternalActionChunk (CreatureActions.HIDE_JEWEL.ToString ());
            outputEatFood = World.NewExternalActionChunk (CreatureActions.EAT_FOOD.ToString ());
            outputGoToDeliverySpot = World.NewExternalActionChunk (CreatureActions.GO_TO_DELIVERY_SPOT.ToString ());
            outputDeliverLeaflet = World.NewExternalActionChunk (CreatureActions.DELIVER_LEAFLET.ToString ());

            //Create thread to simulation
            runThread = new Thread (CognitiveCycle);
            Console.WriteLine ("Agent started");
        }
        #endregion

        #region Public Methods
        /// <summary>
        /// Run the Simulation in World Server 3d Environment
        /// </summary>
        public void Run () {
            Console.WriteLine ("Running ...");
            // Setup Agent to run
            if (runThread != null && !runThread.IsAlive) {
                SetupAgentInfraStructure ();
                // Start Simulation Thread                
                runThread.Start (null);
            }
        }

        /// <summary>
        /// Abort the current Simulation
        /// </summary>
        /// <param name="deleteAgent">If true beyond abort the current simulation it will die the agent.</param>
        public void Abort (Boolean deleteAgent) {
            Console.WriteLine ("Aborting ...");
            if (runThread != null && runThread.IsAlive) {
                runThread.Abort ();
            }

            if (CurrentAgent != null && deleteAgent) {
                CurrentAgent.Die ();
            }
        }

        IList<Thing> processSensoryInformation () {
            IList<Thing> response = null;

            if (worldServer != null && worldServer.IsConnected) {
                response = worldServer.SendGetCreatureState (creatureName);
                prad = (Math.PI / 180) * response.First ().Pitch;
                while (prad > Math.PI) prad -= 2 * Math.PI;
                while (prad < -Math.PI) prad += 2 * Math.PI;
                Sack s = worldServer.SendGetSack ("0");
                mind.setBag (s);
            }

            return response;
        }

        void processSelectedAction (CreatureActions externalAction, IList<Thing> currentSceneInWS3D) {
            Thread.CurrentThread.CurrentCulture = new CultureInfo ("en-US");
            if (worldServer != null && worldServer.IsConnected) {

                // Get Creature
                Creature creature = (Creature) currentSceneInWS3D.Where (item => (item.CategoryId == Thing.CATEGORY_CREATURE)).First ();
                Thing thing = null;

                switch (externalAction) {
                    case CreatureActions.DO_NOTHING:
                        // Do nothing as the own value says
                        break;
                    case CreatureActions.ROTATE_CLOCKWISE:
                        Console.WriteLine ("ROTATE");
                        worldServer.SendSetAngle (creatureId, 2, -2, 2);
                        break;
                    case CreatureActions.WANDER:
                        if (!isLeafletReady (creature)) {
                            Console.WriteLine ("WANDER");
                            worldServer.SendSetAngle (creatureId, 2, -2, 2);
                        }
                        break;
                    case CreatureActions.GO_TO_DELIVERY_SPOT:
                        //thing = currentSceneInWS3D.Where (deliverySpotAway => (deliverySpotAway.CategoryId == Thing.CATEGORY_DeliverySPOT && deliverySpotAway.DistanceToCreature > 30 && isLeafletReady (creature))).FirstOrDefault ();
                        if (isLeafletReady (creature)) {
                            Console.WriteLine ("GO TO DELIVERY SPOT");
                            worldServer.SendSetGoTo (creatureId, 2, 2, 0, 0);
                        }

                        break;
                    case CreatureActions.GO_TO_DESIRED_JEWEL:
                        thing = currentSceneInWS3D.Where (itemToMoveTo => (itemToMoveTo.CategoryId == Thing.CATEGORY_JEWEL && itemToMoveTo.DistanceToCreature > 30 && isDesired (creature, itemToMoveTo.Material.Color))).FirstOrDefault ();
                        if (thing != null) {
                            Console.WriteLine ("GO TO THIS JEWEL> " + thing.Name + " COLOR> " + thing.Material.Color + " X> " + thing.comX + " Y> " + thing.comY);
                            worldServer.SendSetGoTo (creatureId, 2, 2, thing.comX, thing.comY);
                        }
                        break;
                    case CreatureActions.GET_JEWEL:

                        thing = currentSceneInWS3D.Where (itemToGet => (itemToGet.CategoryId == Thing.CATEGORY_JEWEL && itemToGet.DistanceToCreature <= 30 && isDesired (creature, itemToGet.Material.Color))).FirstOrDefault ();
                        if (thing != null) {
                            Console.WriteLine ("GET THIS JEWEL> " + thing.Name + " COLOR> " + thing.Material.Color);
                            worldServer.SendSackIt (creatureId, thing.Name);
                        }
                        break;
                    case CreatureActions.HIDE_JEWEL:
                        thing = currentSceneInWS3D.Where (itemToHide => (itemToHide.CategoryId == Thing.CATEGORY_JEWEL && itemToHide.DistanceToCreature <= 30 && !isDesired (creature, itemToHide.Material.Color))).FirstOrDefault ();
                        if (thing != null) {
                            Console.WriteLine ("HIDE THIS JEWEL> " + thing.Name + " COLOR> " + thing.Material.Color);
                            worldServer.SendHideIt (creatureId, thing.Name);
                        }
                        break;
                    case CreatureActions.EAT_FOOD:
                        thing = currentSceneInWS3D.Where (itemToEat => ((itemToEat.CategoryId == Thing.CATEGORY_NPFOOD || itemToEat.CategoryId == Thing.CATEGORY_PFOOD) && itemToEat.DistanceToCreature <= 30)).FirstOrDefault ();
                        if (thing != null) {
                            Console.WriteLine ("EAT THIS FOOD> " + thing.Name);
                            worldServer.SendEatIt (creatureId, thing.Name);
                        }
                        break;
                    case CreatureActions.DELIVER_LEAFLET:
                        if (creature.X1 < 30 && creature.Y1 < 30) {
                            string leafletId = getMostValuableLeaflet (creature).leafletID.ToString ();
                            string leafletPayment = getMostValuableLeaflet (creature).payment.ToString ();
                            Console.WriteLine ("DELIVER LEAFLET> " + leafletId + " PAYMENT > " + leafletPayment);
                            worldServer.SendDeliverLeaflet (creatureId, leafletId);
                            worldServer.SendStopCreature (creatureId);
							CurrentAgent.Die ();
                        }

                        break;
                    default:
                        break;
                }

                lastAction = externalAction;
            }
        }

        private Boolean isLeafletReady (Creature creature) {

            Leaflet leaflet = getMostValuableLeaflet (creature);
            if (leaflet != null) {

                if (leaflet.getRequired ("Red") <= leaflet.getCollected ("Red") && leaflet.getRequired ("Green") <= leaflet.getCollected ("Green") && leaflet.getRequired ("Blue") <= leaflet.getCollected ("Blue") && leaflet.getRequired ("Yellow") <= leaflet.getCollected ("Yellow") && leaflet.getRequired ("Magenta") <= leaflet.getCollected ("Magenta") && leaflet.getRequired ("White") <= leaflet.getCollected ("White")) {
                    return true;
                }
            }

            return false;
        }

        private Leaflet getMostValuableLeaflet (Creature creature) {
            Leaflet leaflet = null;
            int price = -1;
            if (creature != null) {
                foreach (Leaflet l in creature.getLeaflets ()) {
                    if (l.payment > price) {
                        leaflet = l;
                        price = l.payment;
                    }
                }
            }

            return leaflet;
        }

        private Boolean isDesired (Creature creature, string color) {

            Leaflet leaflet = getMostValuableLeaflet (creature);
            if (leaflet != null) {
                int desiredQuantity = leaflet.getRequired (color);
                int collectedQuantity = leaflet.getCollected (color);
                if (desiredQuantity > 0 && collectedQuantity < desiredQuantity) return true;
            }
            return false;
        }

        #endregion

        #region Setup Agent Methods
        /// <summary>
        /// Setup agent infra structure (ACS, NACS, MS and MCS)
        /// </summary>
        private void SetupAgentInfraStructure () {
			// Setup the ACS Subsystem
			SetupMS ();
        }

        private void SetupMS () {
			//RichDrive
			SimplifiedQBPNetwork net = AgentInitializer.InitializeImplicitDecisionNetwork (CurrentAgent, SimplifiedQBPNetwork.Factory);
			net.Input.Add (inputWallAhead);
			net.Input.Add (inputDesiredJewelAhead);
			net.Input.Add (inputDesiredJewelAway);
			net.Input.Add (inputUndesiredJewelAhead);
			net.Input.Add (inputNothingDesiredAhead);
			net.Input.Add (inputDeliverySpotAhead);
			net.Input.Add (inputDeliverLeaflet);

			net.Output.Add (outputRotateClockwise);
			net.Output.Add (outputGetJewel);
			net.Output.Add (outputGoToDesiredJewel);
			net.Output.Add (outputHideJewel);
			net.Output.Add (outputWander);
			net.Output.Add (outputGoToDeliverySpot);
			net.Output.Add (outputDeliverLeaflet);

			CurrentAgent.Commit (net);

			net.Parameters.LEARNING_RATE = 1;
			CurrentAgent.ACS.Parameters.PERFORM_RER_REFINEMENT = true;

			Console.WriteLine ("Training Neural Network");
			Random rand = new Random ();
			SensoryInformation si;
			ExternalActionChunk chosen;
			int consoleLine;
			int CorrectCounter = 0;
			int numberOfEpoch = 10;
			int numberOfTries = 10000;

			for( int j = 0; j < numberOfEpoch; j++) {
				Console.WriteLine ("============= EPOCH " + (j+1) + " ===========");
				Console.WriteLine ("");
				for (int i = 0; i < numberOfTries; i++) {
					//Console.Write (new String ('\b', Console.WindowWidth));
					si = World.NewSensoryInformation (CurrentAgent);
					double n = rand.NextDouble ();
					// If there's nothing interesting ahead
					if (n < 0.143) {
						si.Add (inputNothingDesiredAhead, 0.3);
						si.Add (inputWallAhead, CurrentAgent.Parameters.MIN_ACTIVATION);
						si.Add (inputDesiredJewelAhead, CurrentAgent.Parameters.MIN_ACTIVATION);
						si.Add (inputDesiredJewelAway, CurrentAgent.Parameters.MIN_ACTIVATION);
						si.Add (inputUndesiredJewelAhead, CurrentAgent.Parameters.MIN_ACTIVATION);
						si.Add (inputDeliverySpotAhead, CurrentAgent.Parameters.MIN_ACTIVATION);
						si.Add (inputDeliverLeaflet, CurrentAgent.Parameters.MIN_ACTIVATION);
					}
					//If there's a Wall Ahead
					else if (n < 0.286) {
						si.Add (inputWallAhead, CurrentAgent.Parameters.MAX_ACTIVATION);
						si.Add (inputNothingDesiredAhead, CurrentAgent.Parameters.MIN_ACTIVATION);
						si.Add (inputDesiredJewelAhead, CurrentAgent.Parameters.MIN_ACTIVATION);
						si.Add (inputDesiredJewelAway, CurrentAgent.Parameters.MIN_ACTIVATION);
						si.Add (inputUndesiredJewelAhead, CurrentAgent.Parameters.MIN_ACTIVATION);
						si.Add (inputDeliverySpotAhead, CurrentAgent.Parameters.MIN_ACTIVATION);
						si.Add (inputDeliverLeaflet, CurrentAgent.Parameters.MIN_ACTIVATION);
					}
					//If there's a Desired Jewel Ahead
					else if (n < 0.429) {
						si.Add (inputDesiredJewelAhead, CurrentAgent.Parameters.MAX_ACTIVATION);
						si.Add (inputNothingDesiredAhead, CurrentAgent.Parameters.MIN_ACTIVATION);
						si.Add (inputWallAhead, CurrentAgent.Parameters.MIN_ACTIVATION);
						si.Add (inputDesiredJewelAway, CurrentAgent.Parameters.MIN_ACTIVATION);
						si.Add (inputUndesiredJewelAhead, CurrentAgent.Parameters.MIN_ACTIVATION);
						si.Add (inputDeliverySpotAhead, CurrentAgent.Parameters.MIN_ACTIVATION);
						si.Add (inputDeliverLeaflet, CurrentAgent.Parameters.MIN_ACTIVATION);
					}
					//If there's a Desired Jewel Away
					else if (n < 0.572) {
						si.Add (inputDesiredJewelAway, CurrentAgent.Parameters.MAX_ACTIVATION);
						si.Add (inputNothingDesiredAhead, CurrentAgent.Parameters.MIN_ACTIVATION);
						si.Add (inputWallAhead, CurrentAgent.Parameters.MIN_ACTIVATION);
						si.Add (inputDesiredJewelAhead, CurrentAgent.Parameters.MIN_ACTIVATION);
						si.Add (inputUndesiredJewelAhead, CurrentAgent.Parameters.MIN_ACTIVATION);
						si.Add (inputDeliverySpotAhead, CurrentAgent.Parameters.MIN_ACTIVATION);
						si.Add (inputDeliverLeaflet, CurrentAgent.Parameters.MIN_ACTIVATION);
					}
					//If there's a Undesired Jewel Ahead
					else if ( n< 0.715){
						si.Add (inputUndesiredJewelAhead, CurrentAgent.Parameters.MAX_ACTIVATION);
						si.Add (inputNothingDesiredAhead, CurrentAgent.Parameters.MIN_ACTIVATION);
						si.Add (inputWallAhead, CurrentAgent.Parameters.MIN_ACTIVATION);
						si.Add (inputDesiredJewelAhead, CurrentAgent.Parameters.MIN_ACTIVATION);
						si.Add (inputDesiredJewelAway, CurrentAgent.Parameters.MIN_ACTIVATION);
						si.Add (inputDeliverySpotAhead, CurrentAgent.Parameters.MIN_ACTIVATION);
						si.Add (inputDeliverLeaflet, CurrentAgent.Parameters.MIN_ACTIVATION);
					}
					//If there's a delivery spot Ahead
					else if ( n< 0.858) {
						si.Add (inputDeliverySpotAhead, CurrentAgent.Parameters.MAX_ACTIVATION);
						si.Add (inputUndesiredJewelAhead, CurrentAgent.Parameters.MIN_ACTIVATION);
						si.Add (inputNothingDesiredAhead, CurrentAgent.Parameters.MIN_ACTIVATION);
						si.Add (inputWallAhead, CurrentAgent.Parameters.MIN_ACTIVATION);
						si.Add (inputDesiredJewelAhead, CurrentAgent.Parameters.MIN_ACTIVATION);
						si.Add (inputDesiredJewelAway, CurrentAgent.Parameters.MIN_ACTIVATION);
						si.Add (inputDeliverLeaflet, CurrentAgent.Parameters.MIN_ACTIVATION);
					}
					//If tdeliver leaflet
					else {
						si.Add (inputDeliverLeaflet, CurrentAgent.Parameters.MIN_ACTIVATION);
						si.Add (inputUndesiredJewelAhead, CurrentAgent.Parameters.MAX_ACTIVATION);
						si.Add (inputNothingDesiredAhead, CurrentAgent.Parameters.MIN_ACTIVATION);
						si.Add (inputWallAhead, CurrentAgent.Parameters.MIN_ACTIVATION);
						si.Add (inputDesiredJewelAhead, CurrentAgent.Parameters.MIN_ACTIVATION);
						si.Add (inputDesiredJewelAway, CurrentAgent.Parameters.MIN_ACTIVATION);
						si.Add (inputDeliverySpotAhead, CurrentAgent.Parameters.MIN_ACTIVATION);
					}

					CurrentAgent.Perceive (si);

					chosen = CurrentAgent.GetChosenExternalAction (si);

					if (chosen == outputWander) {
						if (si [inputNothingDesiredAhead] == CurrentAgent.Parameters.MAX_ACTIVATION) {
							CorrectCounter++;
							CurrentAgent.ReceiveFeedback (si, 1.0);
						} else CurrentAgent.ReceiveFeedback (si, 0.0);
					} else if (chosen == outputRotateClockwise) {
						if (si [inputWallAhead] == CurrentAgent.Parameters.MAX_ACTIVATION) {
							CorrectCounter++;
							CurrentAgent.ReceiveFeedback (si, 1.0);
						} else CurrentAgent.ReceiveFeedback (si, 0.0);
					} else if (chosen == outputGetJewel) {
						if (si [inputDesiredJewelAhead] == CurrentAgent.Parameters.MAX_ACTIVATION) {
							CorrectCounter++;
							CurrentAgent.ReceiveFeedback (si, 1.0);
						} else CurrentAgent.ReceiveFeedback (si, 0.0);
					} else if (chosen == outputGoToDesiredJewel) {
						if (si [inputDesiredJewelAway] == CurrentAgent.Parameters.MAX_ACTIVATION) {
							CorrectCounter++;
							CurrentAgent.ReceiveFeedback (si, 1.0);
						} else CurrentAgent.ReceiveFeedback (si, 0.0);
					} else if (chosen == outputHideJewel) {
						if (si [inputUndesiredJewelAhead] == CurrentAgent.Parameters.MAX_ACTIVATION) {
							CorrectCounter++;
							CurrentAgent.ReceiveFeedback (si, 1.0);
						} else CurrentAgent.ReceiveFeedback (si, 0.0);
					}

					double progress = (int)(((double)(i + 1) / (double)numberOfTries) * 100);
					Console.Write ("> "+progress+"%");
					Console.SetCursorPosition (0, Console.CursorTop -1);
					consoleLine = Console.CursorTop;
					Console.SetCursorPosition (0, Console.CursorTop);
					Console.Write (new String (' ', Console.WindowWidth));
					Console.SetCursorPosition (0, Console.CursorTop);

				}

				Console.WriteLine ("Creature got " + CorrectCounter + " correct out of " + numberOfTries + " trials (" +
					(int)Math.Round (((double)CorrectCounter / (double)numberOfTries) * 100) + "%)");
				CorrectCounter = 0;
			}

		}

        /// <summary>
        /// Setup the ACS subsystem
        /// </summary>
        private void SetupACS () {
            // Create Rule to avoid collision with wall
            SupportCalculator avoidCollisionWallSupportCalculator = FixedRuleToAvoidCollisionWall;
            FixedRule ruleAvoidCollisionWall = AgentInitializer.InitializeActionRule (CurrentAgent, FixedRule.Factory, outputRotateClockwise, avoidCollisionWallSupportCalculator);

            // Commit this rule to Agent (in the ACS)
            CurrentAgent.Commit (ruleAvoidCollisionWall);

            // Create Rule to avoid collision with wall
            SupportCalculator wanderSupportCalculator = FixedRuleToWander;
            FixedRule ruleWander = AgentInitializer.InitializeActionRule (CurrentAgent, FixedRule.Factory, outputWander, wanderSupportCalculator);

            // Commit this rule to Agent (in the ACS)
            CurrentAgent.Commit (ruleWander);

            //Create Rule To Go To Desired Jewel;
            SupportCalculator goToDesiredJewelSupportCalculator = FixedRuleToGoToDesiredJewel;
            FixedRule ruleGoToDesiredJewel = AgentInitializer.InitializeActionRule (CurrentAgent, FixedRule.Factory, outputGoToDesiredJewel, goToDesiredJewelSupportCalculator);

            // Commit this rule to Agent (in the ACS)
            CurrentAgent.Commit (ruleGoToDesiredJewel);

            //Create Rule to go to delivery spot
            SupportCalculator goToDeliverySpotSupportCalculator = FixedRuleToGoToDeliverySpot;
            FixedRule ruleGoToDeliverySpot = AgentInitializer.InitializeActionRule (CurrentAgent, FixedRule.Factory, outputGoToDeliverySpot, goToDeliverySpotSupportCalculator);

            // Commit this rule to Agent (in the ACS)
            CurrentAgent.Commit (ruleGoToDeliverySpot);

            //Create Rule to Get Jewel
            SupportCalculator getJewelSupportCalculator = FixedRuleToGetJewel;
            FixedRule ruleGetJewel = AgentInitializer.InitializeActionRule (CurrentAgent, FixedRule.Factory, outputGetJewel, getJewelSupportCalculator);

            // Commit this rule to Agent (in the ACS)
            CurrentAgent.Commit (ruleGetJewel);

            //Create Rule to hide Jewel
            SupportCalculator hideJewelSupportCalculator = FixedRuleToHideJewel;
            FixedRule ruleHideJewel = AgentInitializer.InitializeActionRule (CurrentAgent, FixedRule.Factory, outputHideJewel, hideJewelSupportCalculator);

            // Commit this rule to Agent (in the ACS)
            CurrentAgent.Commit (ruleHideJewel);

            //Create Rule to eat food
            SupportCalculator eatFoodSupportCalculator = FixedRuleToEatFood;
            FixedRule ruleEatFood = AgentInitializer.InitializeActionRule (CurrentAgent, FixedRule.Factory, outputEatFood, eatFoodSupportCalculator);

            // Commit this rule to Agent (in the ACS)
            CurrentAgent.Commit (ruleEatFood);

            //Create Rule to deliver leaflet
            SupportCalculator deliverLeafletSupporCalculator = FixedRuleToDeliverLeaflet;
            FixedRule ruleDeliverLeaflet = AgentInitializer.InitializeActionRule (CurrentAgent, FixedRule.Factory, outputDeliverLeaflet, deliverLeafletSupporCalculator);

            //Commit this rule to Agent (in the ACS)
            CurrentAgent.Commit (ruleDeliverLeaflet);

            // Disable Rule Refinement
            CurrentAgent.ACS.Parameters.PERFORM_RER_REFINEMENT = false;

            // The selection type will be probabilistic
            CurrentAgent.ACS.Parameters.LEVEL_SELECTION_METHOD = ActionCenteredSubsystem.LevelSelectionMethods.STOCHASTIC;

            // The action selection will be fixed (not variable) i.e. only the statement defined above.
            CurrentAgent.ACS.Parameters.LEVEL_SELECTION_OPTION = ActionCenteredSubsystem.LevelSelectionOptions.FIXED;

            // Define Probabilistic values
            CurrentAgent.ACS.Parameters.FIXED_FR_LEVEL_SELECTION_MEASURE = 1;
            CurrentAgent.ACS.Parameters.FIXED_IRL_LEVEL_SELECTION_MEASURE = 0;
            CurrentAgent.ACS.Parameters.FIXED_BL_LEVEL_SELECTION_MEASURE = 0;
            CurrentAgent.ACS.Parameters.FIXED_RER_LEVEL_SELECTION_MEASURE = 0;
        }

        /// <summary>
        /// Make the agent perception. In other words, translate the information that came from sensors to a new type that the agent can understand
        /// </summary>
        /// <param name="listOfThings">The information that came from server</param>
        /// <returns>The perceived information</returns>
        private SensoryInformation prepareSensoryInformation (IList<Thing> listOfThings) {
            // New sensory information
            SensoryInformation si = World.NewSensoryInformation (CurrentAgent);
            // Get Creature
            Creature creature = (Creature) listOfThings.Where (c => (c.CategoryId == Thing.CATEGORY_CREATURE)).First ();

            // Detect if we have a wall ahead
            Boolean wallAhead = listOfThings.Where (wall => (wall.CategoryId == Thing.CATEGORY_BRICK && wall.DistanceToCreature <= 61)).Any ();
            double wallAheadActivationValue = wallAhead ? CurrentAgent.Parameters.MAX_ACTIVATION : CurrentAgent.Parameters.MIN_ACTIVATION;
            si.Add (inputWallAhead, wallAheadActivationValue);

            //Detect if we have a desired jewel Ahead
            Boolean desiredJewelAhead = listOfThings.Where (itemToGetFar => (itemToGetFar.CategoryId == Thing.CATEGORY_JEWEL && itemToGetFar.DistanceToCreature > 30 && isDesired (creature, itemToGetFar.Material.Color))).Any ();
            double desiredJewelAheadActivationValue = desiredJewelAhead ? CurrentAgent.Parameters.MAX_ACTIVATION : CurrentAgent.Parameters.MIN_ACTIVATION;
            si.Add (inputDesiredJewelAhead, desiredJewelAheadActivationValue);

            //Detect if we have a desired jewel in reach area
            Boolean desiredJewelReachArea = listOfThings.Where (itemToGet => (itemToGet.CategoryId == Thing.CATEGORY_JEWEL && itemToGet.DistanceToCreature <= 30 && isDesired (creature, itemToGet.Material.Color))).Any ();
            double desiredJewelReachAreaActivationValue = desiredJewelReachArea ? CurrentAgent.Parameters.MAX_ACTIVATION : CurrentAgent.Parameters.MIN_ACTIVATION;
            si.Add (inputDesiredJewelAhead, desiredJewelReachAreaActivationValue);

            //Detect if we have a undesired jewel in reach area
            Boolean undesiredJewelReachArea = listOfThings.Where (itemToHide => (itemToHide.CategoryId == Thing.CATEGORY_JEWEL && itemToHide.DistanceToCreature <= 30 && !isDesired (creature, itemToHide.Material.Color))).Any ();
            double undesiredJewelReachAreaActivationValue = undesiredJewelReachArea ? CurrentAgent.Parameters.MAX_ACTIVATION : CurrentAgent.Parameters.MIN_ACTIVATION;
            si.Add (inputUndesiredJewelAhead, undesiredJewelReachAreaActivationValue);

            //Detect if we have a food in reach area
            Boolean foodReachArea = listOfThings.Where (itemToEat => ((itemToEat.CategoryId == Thing.CATEGORY_NPFOOD || itemToEat.CategoryId == Thing.CATEGORY_PFOOD) && itemToEat.DistanceToCreature <= 30)).Any ();
            double foodReachAreaActivationValue = foodReachArea ? CurrentAgent.Parameters.MAX_ACTIVATION : CurrentAgent.Parameters.MIN_ACTIVATION;
            si.Add (inputFoodAhead, foodReachAreaActivationValue);

            //Detect if a Leaflet is ready and move to a delivery spot
            Boolean moveToDeliverySpot = listOfThings.Where (deliverySpot => (deliverySpot.CategoryId == Thing.CATEGORY_DeliverySPOT && isLeafletReady (creature) && deliverySpot.DistanceToCreature > 30)).Any ();
            double moveToDeliverSpotActivationValue = moveToDeliverySpot ? CurrentAgent.Parameters.MAX_ACTIVATION : CurrentAgent.Parameters.MIN_ACTIVATION;
            si.Add (inputDeliverySpotAhead, moveToDeliverSpotActivationValue);

            //Detect if creature is at a deliveryspot and can deliverLeaflet;
            Boolean deliverLeaflet = listOfThings.Where (deliverySpotAhead => (deliverySpotAhead.CategoryId == Thing.CATEGORY_DeliverySPOT && isLeafletReady (creature) && deliverySpotAhead.DistanceToCreature <= 30)).Any ();
            double deliverLeafletSpotActivationValue = moveToDeliverySpot ? CurrentAgent.Parameters.MAX_ACTIVATION : CurrentAgent.Parameters.MIN_ACTIVATION;
            si.Add (inputDeliverLeaflet, deliverLeafletSpotActivationValue);

            // Detect if we have nothing interesting ahead
            Boolean undesiredThings = listOfThings.Where (undesired => (undesired.CategoryId == Thing.CATEGORY_CREATURE || (undesired.CategoryId == Thing.CATEGORY_BRICK && undesired.DistanceToCreature > 61))).Any ();
            Boolean undesiredJewelAhead = listOfThings.Where (undesiredJewel => (undesiredJewel.CategoryId == Thing.CATEGORY_JEWEL && undesiredJewel.DistanceToCreature > 30 && !isDesired (creature, undesiredJewel.Material.Color))).Any ();
            double nothingDesiredAheadActivationValue = (undesiredThings || undesiredJewelAhead) && !desiredJewelAhead ? CurrentAgent.Parameters.MAX_ACTIVATION : CurrentAgent.Parameters.MIN_ACTIVATION;
            si.Add (inputNothingDesiredAhead, nothingDesiredAheadActivationValue);

            int n = 0;
            foreach (Leaflet l in creature.getLeaflets ()) {
                mind.updateLeaflet (n, l);
                n++;
            }
            return si;
        }

        #endregion

        #region Fixed Rules
        // See partial match threshold to verify what are the rules available for action selection
        private double FixedRuleToAvoidCollisionWall (ActivationCollection currentInput, Rule target) {
            return ((currentInput.Contains (inputWallAhead, CurrentAgent.Parameters.MAX_ACTIVATION))) ? 1.0 : 0.0;
        }

        private double FixedRuleToWander (ActivationCollection currentInput, Rule target) {
            return ((currentInput.Contains (inputNothingDesiredAhead, CurrentAgent.Parameters.MAX_ACTIVATION))) ? 1.0 : 0.0;
        }

        private double FixedRuleToGoToDeliverySpot (ActivationCollection currentInput, Rule target) {
            return ((currentInput.Contains (inputDeliverySpotAhead, CurrentAgent.Parameters.MIN_ACTIVATION))) ? 1.0 : 0.0;
        }

        private double FixedRuleToGoToDesiredJewel (ActivationCollection currentInput, Rule target) {
            return ((currentInput.Contains (inputDesiredJewelAhead, CurrentAgent.Parameters.MIN_ACTIVATION))) ? 1.0 : 0.0;
        }

        private double FixedRuleToGetJewel (ActivationCollection currentInput, Rule target) {
            return ((currentInput.Contains (inputDesiredJewelAhead, CurrentAgent.Parameters.MAX_ACTIVATION))) ? 1.0 : 0.0;
        }

        private double FixedRuleToHideJewel (ActivationCollection currentInput, Rule target) {
            return ((currentInput.Contains (inputUndesiredJewelAhead, CurrentAgent.Parameters.MAX_ACTIVATION))) ? 1.0 : 0.0;
        }

        private double FixedRuleToEatFood (ActivationCollection currentInput, Rule target) {
            return ((currentInput.Contains (inputFoodAhead, CurrentAgent.Parameters.MAX_ACTIVATION))) ? 1.0 : 0.0;
        }

        private double FixedRuleToDeliverLeaflet (ActivationCollection currentInput, Rule target) {
            return ((currentInput.Contains (inputDeliverLeaflet, CurrentAgent.Parameters.MAX_ACTIVATION))) ? 1.0 : 0.0;
        }

        #endregion

        #region Run Thread Method
        private void CognitiveCycle (object obj) {

            Console.WriteLine ("Starting Cognitive Cycle ... press CTRL-C to finish !");
            // Cognitive Cycle starts here getting sensorial information
            while (CurrentCognitiveCycle != MaxNumberOfCognitiveCycles) {
                // Get current sensory information                    
                IList<Thing> currentSceneInWS3D = processSensoryInformation ();

                // Make the perception
                SensoryInformation si = prepareSensoryInformation (currentSceneInWS3D);

                //Perceive the sensory information
                CurrentAgent.Perceive (si);

                //Choose an action
                ExternalActionChunk chosen = CurrentAgent.GetChosenExternalAction (si);

                // Get the selected action
                String actionLabel = chosen.LabelAsIComparable.ToString ();
                CreatureActions actionType = (CreatureActions) Enum.Parse (typeof (CreatureActions), actionLabel, true);

                // Call the output event handler
                processSelectedAction (actionType, currentSceneInWS3D);

                // Increment the number of cognitive cycles
                CurrentCognitiveCycle++;

                //Wait to the agent accomplish his job
                if (TimeBetweenCognitiveCycles > 0) {
                    Thread.Sleep (TimeBetweenCognitiveCycles);
                }
            }
        }
        #endregion

    }
}