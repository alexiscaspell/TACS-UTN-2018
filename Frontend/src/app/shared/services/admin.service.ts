import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class AdminService {

  API = 'http://localhost:8080/utn/crypto-currency/admin/';

  constructor(private http: HttpClient) { }

  getUser(user: string) {
    return this.http.get(this.API + `users?nick=${user}`);
  }

  getUsers() {
    return this.http.get(this.API + 'users/nicks');
  }

  compareUsers(user1: string, user2: string) {
    return this.http.get(this.API + `balance/${user1}/${user2}`);
  }

  getToday() {
    return this.http.get(this.API + 'states?beforeDays=0');
  }

  getThreeDays() {
    return this.http.get(this.API + 'states/?beforeDays=3');
  }

  getLastweek() {
    return this.http.get(this.API + 'states/lastweek');
  }

  getLastMonth() {
    return this.http.get(this.API + 'states/lastmonth');
  }

  getStartTimes() {
    return this.http.get(this.API + 'states/all');
  }

}
