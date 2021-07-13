"use strict";
/// <reference types="knockout" />
function notNull(value) {
    if (value === null || value === undefined) {
        return false;
    }
    return true;
}
function spellSchoolName(school) {
    switch (school) {
        case 1: return 'Physical';
        case 2: return 'Holy';
        case 4: return 'Fire';
        case 8: return 'Nature';
        case 16: return 'Frost';
        case 32: return 'Shadow';
        case 64: return 'Arcane';
        default: return String(school);
    }
}
var EventType;
(function (EventType) {
    EventType[EventType["MeleeDamage"] = 0] = "MeleeDamage";
    EventType[EventType["RangeDamage"] = 1] = "RangeDamage";
    EventType[EventType["SpellMeleeDamage"] = 2] = "SpellMeleeDamage";
    EventType[EventType["SpellRangeDamage"] = 3] = "SpellRangeDamage";
    EventType[EventType["Heal"] = 4] = "Heal";
    EventType[EventType["PeriodicDamage"] = 5] = "PeriodicDamage";
    EventType[EventType["PeriodicHeal"] = 6] = "PeriodicHeal";
    EventType[EventType["ApplyAura"] = 7] = "ApplyAura";
    EventType[EventType["RemoveAura"] = 8] = "RemoveAura";
    EventType[EventType["Died"] = 9] = "Died";
})(EventType || (EventType = {}));
function formatEvent(event, target, board, spell) {
    switch (event.type) {
        case EventType.MeleeDamage:
            return board.get(event.casterBoardIndex) + " (" + event.casterBoardIndex + ") meleed (" + event.spellID + ") " + board.get(target.boardIndex) + " (" + target.boardIndex + ") for " + target.points + " damage (" + target.newHealth + "/" + target.maxHealth + ")";
        case EventType.RangeDamage:
            return board.get(event.casterBoardIndex) + " (" + event.casterBoardIndex + ") shot (" + event.spellID + ") " + board.get(target.boardIndex) + " (" + target.boardIndex + ") for " + target.points + " damage (" + target.newHealth + "/" + target.maxHealth + ")";
        case EventType.SpellMeleeDamage:
            return board.get(event.casterBoardIndex) + " (" + event.casterBoardIndex + ") cast " + spell.get(event.spellID) + " (" + event.spellID + ") at " + board.get(target.boardIndex) + " (" + target.boardIndex + ") for " + target.points + " " + spellSchoolName(event.schoolMask) + " damage (" + target.newHealth + "/" + target.maxHealth + ")";
        case EventType.SpellRangeDamage:
            return board.get(event.casterBoardIndex) + " (" + event.casterBoardIndex + ") cast " + spell.get(event.spellID) + " (" + event.spellID + ") at " + board.get(target.boardIndex) + " (" + target.boardIndex + ") for " + target.points + " " + spellSchoolName(event.schoolMask) + " damage (" + target.newHealth + "/" + target.maxHealth + ")";
        case EventType.PeriodicDamage:
            return board.get(event.casterBoardIndex) + "'s (" + event.casterBoardIndex + ") " + spell.get(event.spellID) + " (" + event.spellID + ") dealt " + target.points + " " + spellSchoolName(event.schoolMask) + " to " + board.get(target.boardIndex) + " (" + target.boardIndex + ") (" + target.newHealth + "/" + target.maxHealth + ")";
        case EventType.ApplyAura:
            return board.get(event.casterBoardIndex) + " (" + event.casterBoardIndex + ") applied " + spell.get(event.spellID) + " (" + event.spellID + ") to " + board.get(target.boardIndex) + " (" + target.boardIndex + ")";
        case EventType.RemoveAura:
            return board.get(event.casterBoardIndex) + " (" + event.casterBoardIndex + ") removed " + spell.get(event.spellID) + " (" + event.spellID + ") from " + board.get(target.boardIndex) + " (" + target.boardIndex + ")";
        case EventType.Heal:
            return board.get(event.casterBoardIndex) + " (" + event.casterBoardIndex + ") cast " + spell.get(event.spellID) + " (" + event.spellID + ") on " + board.get(target.boardIndex) + " (" + target.boardIndex + ") for " + target.points + " healing (" + target.newHealth + "/" + target.maxHealth + ")";
        case EventType.PeriodicHeal:
            return board.get(event.casterBoardIndex) + "'s (" + event.casterBoardIndex + ") " + spell.get(event.spellID) + " (" + event.spellID + ") healed " + board.get(target.boardIndex) + " (" + target.boardIndex + ") for " + target.points + " (" + target.newHealth + "/" + target.maxHealth + ")";
        case EventType.Died:
            return board.get(event.casterBoardIndex) + "'s (" + event.casterBoardIndex + ") killed " + board.get(target.boardIndex) + " (" + target.boardIndex + ")";
    }
}
var ViewModel = /** @class */ (function () {
    function ViewModel() {
        this.input = ko.observable();
        this.views = ko.observableArray();
        this.input.extend({ rateLimit: { timeout: 100, method: 'notifyWhenChangesStop' } });
        this.input.subscribe(this.update, this);
    }
    ViewModel.prototype.update = function (value) {
        var _a;
        if (value === undefined) {
            return;
        }
        var missions = value.split(/\r?\n/)
            .map(function (v) {
            try {
                var data = JSON.parse(v);
                data.board = new Map();
                data.spell = new Map();
                data.encounters.forEach(function (e) {
                    data.board.set(e.boardIndex, e.name);
                    data.spell.set(e.autoCombatAutoAttack.autoCombatSpellID, e.autoCombatAutoAttack.name);
                    e.autoCombatSpells.forEach(function (s) {
                        data.spell.set(s.autoCombatSpellID, s.name);
                    });
                });
                Object.values(data.followers).forEach(function (f) {
                    data.board.set(f.boardIndex, f.name);
                    f.spells.forEach(function (s) {
                        data.spell.set(s.autoCombatSpellID, s.name);
                    });
                });
                return data;
            }
            catch (e) {
                console.log(e);
                return null;
            }
        })
            .filter(notNull);
        this.views.removeAll();
        (_a = this.views).push.apply(_a, missions);
    };
    return ViewModel;
}());
ko.applyBindings(new ViewModel());
