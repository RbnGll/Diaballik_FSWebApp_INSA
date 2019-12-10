import {Component, OnInit, AfterViewInit, ViewChildren, QueryList, ElementRef} from '@angular/core';
import {MyData} from '../mydata';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import {error} from "util";

@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.css']
})
export class BoardComponent implements OnInit, AfterViewInit {

  @ViewChildren('cells')
  private cells: QueryList<ElementRef>;

  @ViewChildren('pieces')
  private pieces: QueryList<ElementRef>;

  @ViewChildren('ballPlayer1')
  private ballPlayer1: ElementRef;

  @ViewChildren('ballPlayer2')
  private ballPlayer2: ElementRef;

  private fromElement: Element;
  private toElement: Element;

  constructor(private http: HttpClient, private router: Router, private data: MyData) {
    this.fromElement = this.toElement = null;
  }

  ngOnInit() {
  }

  ngAfterViewInit(): void {
    // Mettre ici les listeners sur les onHover
  }

  cellClick(event: MouseEvent): void {
    const element = (event.currentTarget as Element);

    // Si une cellule est cliquée alors qu'une pièce est déjà sélectionnée, on déplace la pièce
    if (this.fromElement !== null && this.fromElement.classList.contains('piece')) {
      // Send request to the server
      this.toElement = element;

      const toX: string = this.toElement.getAttribute('data-x');
      const toY: string = this.toElement.getAttribute('data-y');
      const fromX: string = this.fromElement.getAttribute('data-x');
      const fromY: string = this.fromElement.getAttribute('data-y');

      this.http.put(`/game/action/movePiece/${fromX}/${fromY}/${toX}/${toY}`, {}, {}).
      subscribe(returnedData => this.data.game = returnedData, error1 => console.log(error1.error));

      console.log(`/game/action/movePiece/${fromX}/${fromY}/${toX}/${toY}`);
    }

    this.fromElement = null;
    this.toElement = null;

    console.log('Cell click', this.fromElement, this.toElement);
  }

  pieceClick(event: MouseEvent): void {
    const element = (event.currentTarget as Element);

    // Si une pièce est cliquée alors qu'une balle est déjà sélectionnée, on déplace la balle
    if (this.fromElement !== null && this.fromElement.classList.contains('ball')) {
      this.toElement = element;

      const toX: string = this.toElement.getAttribute('data-x');
      const toY: string = this.toElement.getAttribute('data-y');
      const fromX: string = this.fromElement.getAttribute('data-x');
      const fromY: string = this.fromElement.getAttribute('data-y');

      this.http.put(`/game/action/passBall/${fromX}/${fromY}/${toX}/${toY}`, {}, {}).
      subscribe(returnedData => this.data.game = returnedData, error1 => console.log(error1));

      console.log(`/game/action/passBall/${fromX}/${fromY}/${toX}/${toY}`);

      this.fromElement = null;
      this.toElement = null;
    } else {
      // Si une pièce est cliquée alors que rien n'est sélectionné
      this.fromElement = element;
    }
    console.log('Piece click', this.fromElement, this.toElement);
  }

  ballClick(event: MouseEvent): void {
    const element = (event.currentTarget as Element);

    // Si une balle est sélectionnée alors que rien n'était sélectionné
    if (this.fromElement === null) {
      this.fromElement = element;
    } else {
      // Si il y avait déjà quelque chose de sélectionné auparavant
      this.fromElement = this.toElement = null;
    }

    console.log('Ball click', this.fromElement, this.toElement);
  }

  isPresentPiece(x: number, y: number, player: boolean): boolean {
    const playerData: any = player ? this.data.game.player1 : this.data.game.player2;
    return playerData.ball.piece.tile.x === x && playerData.ball.piece.tile === y;
  }

}
