import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { AlertService } from '../../shared/services/alert.service';
import { UserService } from '../../shared/services/user.service';

@Component({
  selector: 'app-relog',
  templateUrl: './relog.component.html',
  styleUrls: ['../sign.component.scss']
})
export class RelogComponent {

  loading;

  constructor(public alertService: AlertService, private userService: UserService, public router: Router) { }

  onSubmit(form: NgForm) {
    this.loading = true;
    this.userService.relog(form.value.nick).subscribe(
      success => this.alertService.success('Se envió un mail con su nueva contraseña.'),
      error => {
        this.alertService.error(error.error.message);
        this.loading = false;
      },
      () => this.router.navigate(['login'])
    );
  }

}
