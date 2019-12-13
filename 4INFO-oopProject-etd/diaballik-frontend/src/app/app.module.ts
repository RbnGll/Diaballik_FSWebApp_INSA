import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MenuComponent } from './menu/menu.component';
import { MyData } from './mydata';
import { HomeComponent } from './home/home.component';
import { PersonalisationComponent } from './personalisation/personalisation.component';
import { GameComponent } from './game/game.component';
import {FormsModule} from '@angular/forms';
import {ReactiveFormsModule} from '@angular/forms';
import { Board2Component } from './board2/board2.component';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import { VictoryComponent } from './victory/victory.component';
import {NotifierModule} from "angular-notifier";

@NgModule({
  declarations: [
    AppComponent,
    MenuComponent,
    HomeComponent,
    PersonalisationComponent,
    GameComponent,
    Board2Component,
    VictoryComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModule,
    NotifierModule.withConfig({
      position: {
        horizontal: {
          position: 'right'
        }
      },
      theme: 'material',
      behaviour: {
        autoHide: 2500,
        onMouseover: 'pauseAutoHide',
        stacking: 2
      }
    })
  ],
  providers: [MyData],
  bootstrap: [AppComponent]
})
export class AppModule { }
