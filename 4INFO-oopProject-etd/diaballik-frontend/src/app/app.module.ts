import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BoardComponent } from './board/board.component';
import { MenuComponent } from './menu/menu.component';
import { MyData } from './mydata';
import { HomeComponent } from './home/home.component';
import { PlayerSelectionComponent } from './player-selection/player-selection.component';
import { PersonalisationComponent } from './personalisation/personalisation.component';
import { GameComponent } from './game/game.component';
import {FormsModule} from '@angular/forms';
import {ReactiveFormsModule} from '@angular/forms';
import { Board2Component } from './board2/board2.component';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import { AlertBasicComponent } from './alert-basic/alert-basic.component';
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";

@NgModule({
  declarations: [
    AppComponent,
    BoardComponent,
    MenuComponent,
    HomeComponent,
    PlayerSelectionComponent,
    PersonalisationComponent,
    GameComponent,
    Board2Component,
    AlertBasicComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModule
  ],
  providers: [MyData],
  bootstrap: [AppComponent]
})
export class AppModule { }
