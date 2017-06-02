/************************************************
 * Configuration file for the application
 * 
 * mode: 
 *  if you have configured the kit name (eg : fr | uk | nl) and set the list of cars
 *  mode : uk-> will use the UK cars
 *  mode : fr-> will use the FR cars
 *  mode : nl-> will use the NL cars
 *  node : discover -> will use any "cars" with the risk of conflict
 * 
 * cars : OPTIONAL
 *  contains the list of Car addresses and name
 *  if the cars list is EMPTY : the application will use the devices 
 *  found using the scan. This could be in conflict with other Anki kits
 * 
 *  Set the list of values when you want to force specific cars to be used
 *  by the demo.
 * 
 *  if you are alone just:
 *    - put the mode to "discover"
 *    - or cars should be empty
 * 
 * See below for the values of UK, FR, NL kits
 * 
 * 
 ************************************************/

module.exports = {
    mode: "nl", // discover | uk | fr | nl
    frCars: [{
            "carName": "Skull",
            "address": "de:d5:50:6b:e0:43"
        },
        {
            "carName": "Ground Shock",
            "address": "ca:71:ae:e1:e3:4a"
        }
    ],
    ukCars: [{
            "carName": "Skull",
            "address": "de:b8:24:73:ac:03"
        },
        {
            "carName": "Ground Shock",
            "address": "c6:18:a8:10:41:26",
        },
        {
            "carName": "Thermo",
            "address": "de:8b:c9:54:97:60"
        },
        {
            "carName": "Nuke",
            "address": "e6:25:f8:72:c8:1d",
        }
    ],
    nlCars: [{
            "carName": "Skull",
            "address": "e0:85:e6:99:5a:94"
        },
        {
            "carName": "Ground Shock",
            "address": "fd:0d:76:cd:4b:c5"
        },
        {
            "carName": "Thermo",
            "address": "d5:ae:f9:3b:bc:c9"
        }
    ]    
};

// UK Anki
// [{
//         "carName": "Skull",
//         "address": "de:b8:24:73:ac:03"
//     },
//     {
//         "carName": "Ground Shock",
//         "address": "c6:18:a8:10:41:26",
//     },
//     {
//         "carName": "Thermo",
//         "address": "de:8b:c9:54:97:60"
//     },
//     {
//         "carName": "Nuke",
//         "address": "e6:25:f8:72:c8:1d",
//     }
// ]


/// French Anki Kit
// [
//    {
//         "carName": "Skull",
//         "address": "de:d5:50:6b:e0:43"
//     },
//     {
//         "carName": "Ground Shock",
//         "address": "ca:71:ae:e1:e3:4a"
//     }
// ]
