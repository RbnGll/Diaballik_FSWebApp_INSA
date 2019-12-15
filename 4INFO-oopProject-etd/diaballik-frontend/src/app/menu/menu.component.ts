import {Component, OnInit, AfterViewInit, ViewChild} from '@angular/core';
import {MyData} from '../mydata';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Router} from '@angular/router';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit, AfterViewInit {

  constructor(private http: HttpClient, private router: Router, private data: MyData) { }

  ngOnInit() {
  }

  ngAfterViewInit(): void {

  }

  endTurnClicked(): void {
    this.executePutResquest('/game/action/endTurn');
  }

  undoClicked(): void {
    this.executePutResquest('/game/action/undo');
  }

  redoClicked(): void {
    this.executePutResquest('/game/action/redo');
  }

  onQuitClicked(): void {
    this.router.navigate(['gameSettings']);
  }

  executePutResquest(request: string): void {
    this.http.put(request, {}, {})
      .subscribe(returnedData => this.data.game = returnedData, error => {
        // Traiter les erreurs
        console.log(error);
      });

    console.log(request);
  }

}
