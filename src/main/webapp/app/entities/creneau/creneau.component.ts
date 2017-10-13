import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { Creneau } from './creneau.model';
import { CreneauService } from './creneau.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-creneau',
    templateUrl: './creneau.component.html'
})
export class CreneauComponent implements OnInit, OnDestroy {
creneaus: Creneau[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private creneauService: CreneauService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.creneauService.query().subscribe(
            (res: ResponseWrapper) => {
                this.creneaus = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCreneaus();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Creneau) {
        return item.id;
    }
    registerChangeInCreneaus() {
        this.eventSubscriber = this.eventManager.subscribe('creneauListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
