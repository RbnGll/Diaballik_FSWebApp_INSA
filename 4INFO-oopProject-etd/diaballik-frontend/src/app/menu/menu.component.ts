import { Component, OnInit, AfterViewInit} from '@angular/core';
import {MyData} from '../mydata';
import {HttpClient} from '@angular/common/http';
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

}
