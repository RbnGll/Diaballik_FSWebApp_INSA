import { Injectable } from '@angular/core';

// This class is data that will be used through the different components
// by dependency injection.
// See mycomponent.component.ts for a usage of this class.
// See app.module.ts to see how this injection is configured
@Injectable()
export class MyData {
  // The data to store
  // any: https://www.typescriptlang.org/docs/handbook/basic-types.html
  public game: any;

  public constructor() {
    // An example of how storage can be used to store json data.
    // The JSON class can both parse JSON from a string, a produce a string from a JSON object (stringify)
    this.game = JSON.parse('{\n' +
      '  "player1": {\n' +
      '    "type": "humanPlayer",\n' +
      '    "playerNumber": "1",\n' +
      '    "name": "Ronan",\n' +
      '    "color": 0,\n' +
      '    "victory": false,\n' +
      '    "pieces": [\n' +
      '      {\n' +
      '        "tile": {\n' +
      '          "x": 0,\n' +
      '          "y": 0\n' +
      '        }\n' +
      '      },\n' +
      '      {\n' +
      '        "tile": {\n' +
      '          "x": 1,\n' +
      '          "y": 0\n' +
      '        }\n' +
      '      },\n' +
      '      {\n' +
      '        "tile": {\n' +
      '          "x": 2,\n' +
      '          "y": 0\n' +
      '        }\n' +
      '      },\n' +
      '      {\n' +
      '        "tile": {\n' +
      '          "x": 3,\n' +
      '          "y": 0\n' +
      '        }\n' +
      '      },\n' +
      '      {\n' +
      '        "tile": {\n' +
      '          "x": 4,\n' +
      '          "y": 0\n' +
      '        }\n' +
      '      },\n' +
      '      {\n' +
      '        "tile": {\n' +
      '          "x": 5,\n' +
      '          "y": 0\n' +
      '        }\n' +
      '      },\n' +
      '      {\n' +
      '        "tile": {\n' +
      '          "x": 6,\n' +
      '          "y": 0\n' +
      '        }\n' +
      '      }\n' +
      '    ],\n' +
      '    "ball": {\n' +
      '      "piece": {\n' +
      '        "tile": {\n' +
      '          "x": 3,\n' +
      '          "y": 0\n' +
      '        }\n' +
      '      }\n' +
      '    }\n' +
      '  },\n' +
      '  "player2": {\n' +
      '    "type": "humanPlayer",\n' +
      '    "playerNumber": "2",\n' +
      '    "name": "Robin",\n' +
      '    "color": 1,\n' +
      '    "victory": false,\n' +
      '    "pieces": [\n' +
      '      {\n' +
      '        "tile": {\n' +
      '          "x": 0,\n' +
      '          "y": 6\n' +
      '        }\n' +
      '      },\n' +
      '      {\n' +
      '        "tile": {\n' +
      '          "x": 1,\n' +
      '          "y": 6\n' +
      '        }\n' +
      '      },\n' +
      '      {\n' +
      '        "tile": {\n' +
      '          "x": 2,\n' +
      '          "y": 6\n' +
      '        }\n' +
      '      },\n' +
      '      {\n' +
      '        "tile": {\n' +
      '          "x": 3,\n' +
      '          "y": 6\n' +
      '        }\n' +
      '      },\n' +
      '      {\n' +
      '        "tile": {\n' +
      '          "x": 4,\n' +
      '          "y": 6\n' +
      '        }\n' +
      '      },\n' +
      '      {\n' +
      '        "tile": {\n' +
      '          "x": 5,\n' +
      '          "y": 6\n' +
      '        }\n' +
      '      },\n' +
      '      {\n' +
      '        "tile": {\n' +
      '          "x": 6,\n' +
      '          "y": 6\n' +
      '        }\n' +
      '      }\n' +
      '    ],\n' +
      '    "ball": {\n' +
      '      "piece": {\n' +
      '        "tile": {\n' +
      '          "x": 3,\n' +
      '          "y": 6\n' +
      '        }\n' +
      '      }\n' +
      '    }\n' +
      '  },\n' +
      '  "currentTurn": {\n' +
      '    "turnEnd": false\n' +
      '  },\n' +
      '  "aiGame": false,\n' +
      '  "turnCount": 1,\n' +
      '  "currentPlayer": "1"\n' +
      '}');
  }
}
