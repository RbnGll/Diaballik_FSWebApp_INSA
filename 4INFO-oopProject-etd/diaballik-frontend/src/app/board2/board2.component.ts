import {Component, ElementRef, EventEmitter, OnInit, Output, QueryList, ViewChildren} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import {MyData} from '../mydata';

@Component({
  selector: 'app-board2',
  templateUrl: './board2.component.html',
  styleUrls: ['./board2.component.css']
})
export class Board2Component implements OnInit {

  @ViewChildren('cells')
  private cells: QueryList<ElementRef>;

  @Output()
  victoryEvent = new EventEmitter();

  private fromElement: Element;
  private toElement: Element;

  constructor(private http: HttpClient, private router: Router, private data: MyData) {
    this.fromElement = null;
    this.toElement = null;
  }

  ngOnInit() {
  }

  cellClicked(event: MouseEvent): void {
    // Données factices en JSON sur les tiles possibles après une action
    const jsonTiles: any = [
      {
        x: 1,
        y: 1
      },
      {
        x: 5,
        y: 5
      }
    ];

    // Si une cellule a déjà été sélectionnée
    if (this.fromElement !== null) {
      this.toElement = event.currentTarget as Element;

      const toX: string = this.toElement.getAttribute('data-x');
      const toY: string = this.toElement.getAttribute('data-y');
      const fromX: string = this.fromElement.getAttribute('data-x');
      const fromY: string = this.fromElement.getAttribute('data-y');

      let requete = '';

      // Si la cellule du fromElement contient une balle
      if (this.isPresentBall(Number(fromX), Number(fromY))) {
        requete = `/game/action/passBall/${fromX}/${fromY}/${toX}/${toY}`
      }
      // Sinon c'est un mouvement de pièce
      else {
        requete = `/game/action/movePiece/${fromX}/${fromY}/${toX}/${toY}`
      }

      // Envoi de la requête
      this.executePutResquest(requete);

      this.fromElement = null;

      // Nettoyage des cases possibles
      this.cells.map(elementRef => {
        elementRef.nativeElement.classList.remove('possible');
        elementRef.nativeElement.classList.add('classic');
      });
    }
    // Sinon on se met en attente d'une autre case
    else {
      const cell = event.currentTarget as Element;
      const cellX = Number(cell.getAttribute('data-x'));
      const cellY = Number(cell.getAttribute('data-y'));

      // Si il y a une pièce à l'endroit du premier clic
      if (this.isPresentPiece(cellX, cellY)) {
        this.fromElement = event.currentTarget as Element;
        this.toElement = null;

        // Affichage des possibilités (mise en valeur des cases possibles)
        // TODO: Récupérer à l'aide d'une requête les tiles possibles

        for (const tile of jsonTiles) {
          const element = this.getCell(tile.x, tile.y);
          element.classList.add('possible');
          element.classList.remove('classic');
        }
      }

      // Sinon on ne fait rien

      // TODO : Mettre un listener sur la touche échap si on a sélectionné cette case par inadvertance
    }
  }

  /**
   * Retourne 1 si la pièce appartient au player 1
   * Retourne 2 si la pièce appartient au player 2
   * Retourne 0 sinon
   *
   * @param x: Coordonnée x de la pièce
   * @param y: Coordonnée y de la pièce
   */
  isPresentPiece(x: number, y: number): number {
    const player1: any = this.data.game.player1;
    const player2: any = this.data.game.player2;

    for (const piece of player1.pieces) {
      if (piece.tile.x === x && piece.tile.y === y) {
        return 1;
      }
    }

    for (const piece of player2.pieces) {
      if (piece.tile.x === x && piece.tile.y === y) {
        return 2;
      }
    }

    return 0;
  }

  /**
   * Retourne true si une balle est présente à cette position (de n'importe quel joueur)
   * False sinon
   * @param x: Coordonnée x de la pièce
   * @param y: Coordonnée y de la pièce
   */
  isPresentBall(x: number, y: number): boolean {
    const tileBall1 = this.data.game.player1.ball.piece.tile;
    const tileBall2 = this.data.game.player2.ball.piece.tile;

    return (tileBall1.x === x && tileBall1.y === y) || (tileBall2.x === x && tileBall2.y === y);
  }

  /**
   * Retourne la cellule (Element) correspondante à une coordonnée (x,y) donnée
   *
   * @param x: Coordonnée x de la pièce
   * @param y: Coordonnée y de la pièce
   */
  getCell(x: number, y: number): Element {
    return this.cells.find(elementRef => {
      const element = elementRef.nativeElement;
      return element.getAttribute('data-x') == x && element.getAttribute('data-y') == y;
    }).nativeElement;
  }

  executePutResquest(request: string): void {
    this.http.put(request, {}, {})
      .subscribe(
        returnedData => {
          this.data.game = returnedData;
          // Regarder si il y a une victoire !
          if (this.data.game.player1.victory === true) {
            this.victoryEvent.emit({player: 1}); // On émet un évènement capté par le gameComponent
          } else if (this.data.game.player2.victory === true) {
            this.victoryEvent.emit({player: 2});
          }
        },
          errorObject => {
        // Traiter les erreurs
        console.log(errorObject.error);
      });

    console.log(request);
  }
}
