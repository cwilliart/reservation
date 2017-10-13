import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Creneau } from './creneau.model';
import { CreneauPopupService } from './creneau-popup.service';
import { CreneauService } from './creneau.service';
import { Reservation, ReservationService } from '../reservation';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-creneau-dialog',
    templateUrl: './creneau-dialog.component.html'
})
export class CreneauDialogComponent implements OnInit {

    creneau: Creneau;
    isSaving: boolean;

    reservations: Reservation[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private creneauService: CreneauService,
        private reservationService: ReservationService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.reservationService.query()
            .subscribe((res: ResponseWrapper) => { this.reservations = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.creneau.id !== undefined) {
            this.subscribeToSaveResponse(
                this.creneauService.update(this.creneau));
        } else {
            this.subscribeToSaveResponse(
                this.creneauService.create(this.creneau));
        }
    }

    private subscribeToSaveResponse(result: Observable<Creneau>) {
        result.subscribe((res: Creneau) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Creneau) {
        this.eventManager.broadcast({ name: 'creneauListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackReservationById(index: number, item: Reservation) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-creneau-popup',
    template: ''
})
export class CreneauPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private creneauPopupService: CreneauPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.creneauPopupService
                    .open(CreneauDialogComponent as Component, params['id']);
            } else {
                this.creneauPopupService
                    .open(CreneauDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
