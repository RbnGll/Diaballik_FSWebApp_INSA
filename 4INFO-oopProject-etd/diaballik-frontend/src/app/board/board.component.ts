import {Component, OnInit, AfterViewInit, ViewChildren} from '@angular/core';
import {MyData} from '../mydata';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';

@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.css']
})
export class BoardComponent implements OnInit, AfterViewInit {

  // @ViewChildren('cells')

  constructor(private http: HttpClient, private router: Router, private data: MyData) {
    console.log(this.data.game);

    console.log(this.data.game.player1);
  }

  ngOnInit() {
  }

  ngAfterViewInit(): void {
  }

  processClick(event: MouseEvent): void {
    const element = (event.currentTarget as Element);
    console.log(element.getAttribute('data-x'), element.getAttribute('data-y'));
  }

}
