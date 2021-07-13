/// <reference types="knockout" />

type TargetInfo = {
	boardIndex: number,
	points?: number,
	oldHealth: number,
	newHealth: number,
	maxHealth: number,
};

type LogEvent = {
	casterBoardIndex: number,
	type: EventType,
	schoolMask: number,
	targetInfo: TargetInfo[],
	auraType: number,
	effectIndex: number,
	spellID: number,
};

type MissionData = {
	missionScalar: number,
	log: [{
		events: LogEvent[],
	}],
	addonVersion?: string,
	missionID: number,
	missionName: string,
	meta: {
		dv: number,
		lc: string,
		ts: number,
		cb: string,
	},
	followers: {
		[key: string]: {
			spells: [{
				description: string,
				autoCombatSpellID: number,
				duration: number,
				name: string,
				hasThornsEffect: boolean,
				cooldown: number,
			}],
			role: number,
			name: string,
			boardIndex: number,
			health: number,
			level: number,
			maxHealth: number,
			attack: number,
		},
	},
	encounters: [{
		autoCombatAutoAttack: {
			description: string,
			autoCombatSpellID: number,
			duration: number,
			previewMask: number,
			name: string,
			hasThornsEffect: boolean,
			schoolMask: number,
			icon: number,
			cooldown: number,
			spellTutorialFlag: number,
		},
		isElite: boolean,
		autoCombatSpells: [{
			description: string,
			autoCombatSpellID: number,
			duration: number,
			name: string,
			hasThornsEffect: boolean,
			cooldown: number,
		}],
		health: number,
		role: number,
		name: string,
		boardIndex: number,
		maxHealth: number,
		attack: number,
	}],
	winner: boolean,
	predictionCorrect: boolean,
	differentOutcome: boolean,
	// calculated
	board: Map<number, string>,
	spell: Map<number, string>,
};

function notNull<T>(value: T | null | undefined): value is T {
	if (value === null || value === undefined) {
		return false;
	}
	return true;
}

function spellSchoolName(school: number): string {
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

enum EventType {
	MeleeDamage = 0,
	RangeDamage = 1,
	SpellMeleeDamage = 2,
	SpellRangeDamage = 3,
	Heal = 4,
	PeriodicDamage = 5,
	PeriodicHeal = 6,
	ApplyAura = 7,
	RemoveAura = 8,
	Died = 9,
}

function formatEvent(event: LogEvent, target: TargetInfo, board: Map<number, string>, spell: Map<number, string>): string {
	switch (event.type) {
		case EventType.MeleeDamage:
			return `${board.get(event.casterBoardIndex)} (${event.casterBoardIndex}) meleed (${event.spellID}) ${board.get(target.boardIndex)} (${target.boardIndex}) for ${target.points} damage (${target.newHealth}/${target.maxHealth})`;
		case EventType.RangeDamage:
			return `${board.get(event.casterBoardIndex)} (${event.casterBoardIndex}) shot (${event.spellID}) ${board.get(target.boardIndex)} (${target.boardIndex}) for ${target.points} damage (${target.newHealth}/${target.maxHealth})`;
		case EventType.SpellMeleeDamage:
			return `${board.get(event.casterBoardIndex)} (${event.casterBoardIndex}) cast ${spell.get(event.spellID)} (${event.spellID}) at ${board.get(target.boardIndex)} (${target.boardIndex}) for ${target.points} ${spellSchoolName(event.schoolMask)} damage (${target.newHealth}/${target.maxHealth})`;
		case EventType.SpellRangeDamage:
			return `${board.get(event.casterBoardIndex)} (${event.casterBoardIndex}) cast ${spell.get(event.spellID)} (${event.spellID}) at ${board.get(target.boardIndex)} (${target.boardIndex}) for ${target.points} ${spellSchoolName(event.schoolMask)} damage (${target.newHealth}/${target.maxHealth})`;
		case EventType.PeriodicDamage:
			return `${board.get(event.casterBoardIndex)}'s (${event.casterBoardIndex}) ${spell.get(event.spellID)} (${event.spellID}) dealt ${target.points} ${spellSchoolName(event.schoolMask)} to ${board.get(target.boardIndex)} (${target.boardIndex}) (${target.newHealth}/${target.maxHealth})`;
		case EventType.ApplyAura:
			return `${board.get(event.casterBoardIndex)} (${event.casterBoardIndex}) applied ${spell.get(event.spellID)} (${event.spellID}) to ${board.get(target.boardIndex)} (${target.boardIndex})`;
		case EventType.RemoveAura:
			return `${board.get(event.casterBoardIndex)} (${event.casterBoardIndex}) removed ${spell.get(event.spellID)} (${event.spellID}) from ${board.get(target.boardIndex)} (${target.boardIndex})`;
		case EventType.Heal:
			return `${board.get(event.casterBoardIndex)} (${event.casterBoardIndex}) cast ${spell.get(event.spellID)} (${event.spellID}) on ${board.get(target.boardIndex)} (${target.boardIndex}) for ${target.points} healing (${target.newHealth}/${target.maxHealth})`;
		case EventType.PeriodicHeal:
			return `${board.get(event.casterBoardIndex)}'s (${event.casterBoardIndex}) ${spell.get(event.spellID)} (${event.spellID}) healed ${board.get(target.boardIndex)} (${target.boardIndex}) for ${target.points} (${target.newHealth}/${target.maxHealth})`;
		case EventType.Died:
			return `${board.get(event.casterBoardIndex)}'s (${event.casterBoardIndex}) killed ${board.get(target.boardIndex)} (${target.boardIndex})`;
	}
}

class ViewModel {
	input = ko.observable<string>();
	views = ko.observableArray<MissionData>();

	constructor() {
		this.input.extend({ rateLimit: { timeout: 100, method: 'notifyWhenChangesStop' } });
		this.input.subscribe(this.update, this);
	}

	update(value: string | undefined) {
		if (value === undefined) {
			return;
		}

		var missions = value.split(/\r?\n/)
			.map(v => {
				try {
					var data = JSON.parse(v) as MissionData;
					data.board = new Map<number, string>();
					data.spell = new Map<number, string>();
					data.encounters.forEach(e => {
						data.board.set(e.boardIndex, e.name);
						data.spell.set(e.autoCombatAutoAttack.autoCombatSpellID, e.autoCombatAutoAttack.name);
						e.autoCombatSpells.forEach(s => {
							data.spell.set(s.autoCombatSpellID, s.name);
						});
					});
					Object.values(data.followers).forEach(f => {
						data.board.set(f.boardIndex, f.name);
						f.spells.forEach(s => {
							data.spell.set(s.autoCombatSpellID, s.name);
						});
					});
					return data;
				} catch (e) {
					console.log(e);
					return null;
				}
			})
			.filter(notNull);

		this.views.removeAll();
		this.views.push(...missions);
	}
}

ko.applyBindings(new ViewModel());
