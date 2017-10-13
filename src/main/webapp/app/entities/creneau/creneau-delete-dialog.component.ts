import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Creneau } from './creneau.model';
import { CreneauPopupService } from './creneau-popup.service';
import { CreneauService } from './creneau.service';

@Component({
    selector: 'jhi-creneau-delete-dialog',
    templateUrl: './creneau-delete-dialog.component.html'
})
export class CreneauDeleteDialogComponent {

    creneau: Creneau;

    constructor(
        private creneauService: CreneauService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.creneauService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'creneauListModification',
                content: 'Deleted an creneau'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-creneau-delete-popup',
    template: ''
})
export class CreneauDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private creneauPopupService: CreneauPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.creneauPopupService
                .open(CreneauDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
