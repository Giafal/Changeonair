import { Component, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent {
  @ViewChild('f') form!: NgForm;
  checkbox: boolean = false;
  error: undefined | string;

  constructor(private svc: AuthService, private router: Router) {}

  onSubmit() {
    if (
      this.form.value.username.trim() !== '' &&
      this.form.value.email.trim() !== '' &&
      this.form.value.password.trim() !== '' &&
      this.checkbox
    ) {
      this.svc.signup(this.form.value).subscribe(
        (resp) => {
          console.log(resp);
          this.error = undefined;
          this.router.navigate(['/login']);
        },
        (err) => {
          console.log(err.error.message);
          this.error = err.error.message;
          this.router.navigate(['/login']);
        }
      );
    } else {
      this.error =
        'Devi compilare tutti i campi e accettare i termini e le condizioni';
    }
  }
}
