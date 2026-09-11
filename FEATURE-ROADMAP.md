# GoreeCloud Keyboard — Feature Roadmap

**Status:** Active roadmap control  
**As of:** September 10, 2026  
**Authoritative project record:** `Project Specification — Keyboard`  
**Canonical repository:** `GoreeCloud/goreecloud-keyboard`  
**Drive control:** `GoreeCloud/Feature Roadmap/GoreeCloud Keyboard/FEATURE-ROADMAP.docx`

## Purpose

This file is the repository-side feature roadmap control for GoreeCloud Keyboard. It records current planned and recommended feature work without replacing the authoritative project record, repository implementation evidence, release gates, or GoreeCloud Tasks Management.

This roadmap and the Drive-side `FEATURE-ROADMAP.docx` must remain materially synchronized. No feature is Stable or complete solely because it appears here; lifecycle claims require applicable implementation, validation, review, release, and runtime evidence.

Current Development checkpoint: the active stack is Android-only and remains Draft/nonconformant. Draft PR #63 (`feature/keyboard-spacebar-cursor-control`) final exact head `267a5d1f5642ec535001d40d323736629f56cd99` passed Android CI `34417251761` / #225. Stacked Draft PR #64 (`feature/keyboard-spacebar-cursor-setting`) implementation head `8405916dc181199ea17a8da9653d3806d9ceefaa` passed Android CI `34524507597` / #226 and adds explicit device-local user control for the cursor gesture. Repository-roadmap reconciliation remains governance-only and does not promote the Keyboard beyond Development.

## Status vocabulary

- **Validated development foundation** — source/build behavior has exact-revision Development validation but is not production or Stable acceptance.
- **Active Development** — implementation exists or is being advanced in the current Development stack, with downstream acceptance still open.
- **Planned** — required product work has not yet been established as current implementation.
- **Blocked on prerequisite** — work must not proceed as a production capability until a required authority or platform prerequisite exists.
- **Ongoing control** — governance or safety obligation that continuously applies.

## Roadmap

| ID | Feature / obligation | Priority | Current state |
| --- | --- | --- | --- |
| FR-001 | Reconcile and maintain every current planned or recommended GoreeCloud Keyboard feature from the authoritative project record and verified repository evidence in this roadmap. | High | Ongoing control |
| FR-002 | Move actionable feature obligations into GoreeCloud Tasks Management when required, preserving priority, dependency, and lifecycle disposition. | High | Ongoing control |
| FR-003 | Do not mark features implemented, complete, cancelled, or superseded without authoritative evidence and synchronized repository/Drive roadmap updates. | High | Ongoing control |
| FR-010 | Maintain the first-party Android IME foundation, editor-lifecycle privacy boundaries, sensitive/no-suggestions handling, zero-network local typing path, native key/symbol/emoji surfaces, and Android platform semantics without silently widening editor-data authority. | High | Validated Development foundations exist; production/privacy/platform acceptance remains incomplete |
| FR-011 | Deliver privacy-bounded spacebar cursor control with explicit device-local user control, ordinary Space tap behavior, bounded horizontal movement, fail-closed gesture handling, and no surrounding-text reconstruction. | High | Active Development — PR #63 final head `267a5d1f5642ec535001d40d323736629f56cd99` passed Android CI `34417251761`; PR #64 implementation head `8405916dc181199ea17a8da9653d3806d9ceefaa` passed Android CI `34524507597`. Physical-device ergonomics, RTL/BiDi, accessibility, and release acceptance remain open. |
| FR-012 | Continue local-first typing quality: Unicode-safe input/deletion, stronger autocorrect, deterministic local suggestions, Unicode-normalized matching, correction quality, user dictionaries, and privacy-minimized personalization without retaining typed content by default. | High | Active Development foundation; multilingual/language-aware ranking, broader dictionaries, user-dictionary product UX, and representative acceptance remain open |
| FR-013 | Add first-party multilingual layouts, language switching, locale-aware typing behavior, script-appropriate editing, and RTL/BiDi correctness while preserving sensitive-editor and local-first privacy rules. | High | Planned; source localization/accessibility foundations exist but multilingual product acceptance is not established |
| FR-014 | Add gesture/swipe typing only through a bounded, privacy-reviewed recognition path with explicit local/remote authority and retention rules. | High | Planned; no current swipe-typing implementation claim |
| FR-015 | Continue native emoji, symbol, long-press alternate, category, recents, and discovery improvements, including broader Unicode/grapheme correctness, while keeping local history and portable state explicitly separated. | Medium | Active Development foundations exist; complete catalog/search/composition/accessibility/device acceptance remains open |
| FR-016 | Maintain and expand the configurable utility toolbar only with real implemented actions; do not expose placeholder controls for capabilities whose authority or implementation is absent. | Medium | Active Development — current toolbar candidate exposes only implemented Emoji, Symbols, and Keyboard Settings actions |
| FR-017 | Implement GoreeCloud Secure Paste as an intentional user-mediated paste flow with Privacy Shield authorization and a privileged platform Secure Paste Broker for cross-application enforcement; keep clipboard history a separately governed capability. | High | Blocked on prerequisite — Keyboard alone cannot revoke other applications' Android clipboard authority; privileged platform enforcement, Privacy Shield policy, Wardveil review, sensitive-data behavior, and representative acceptance are required |
| FR-018 | Add privacy-approved voice input and translation only with explicit data-flow, retention, consent, provider, security, offline/degraded-mode, and user-control contracts. | High | Planned; no current voice/translation authority or implementation claim |
| FR-019 | Add one-handed, floating, split, tablet, foldable, posture-aware, compact-width, orientation, and large-display layouts with touch accuracy and reachability preserved across supported form factors. | High | Planned beyond current adaptive foundations; representative phone/tablet/foldable acceptance remains open |
| FR-020 | Expand GoreeCloud Quill-assisted writing beyond bounded local suggestions only when editor-data scope, sensitive-field behavior, user control, privacy authorization, model/provider authority, and degraded behavior are explicit and accepted. | High | Planned beyond the current local suggestion foundation |
| FR-021 | Preserve `goreecloud-keyboard-preferences/1` as an explicitly privacy-minimized portability boundary and advance backup/restore/export only through reviewed schema expansion, validation-before-write, review-before-destination, clean-target recovery rules, and Everkeep acceptance. | High | Active Development foundation is category-only portability; product-wide backup/restore/recovery and Everkeep acceptance remain incomplete |
| FR-022 | Complete application-specific GLAZE UI V1.3 / `1.3.0` acceptance: rendered Light/Dark/Deep Dark behavior where applicable, material/component states, Reduced Motion, Reduced Transparency, Increased Contrast, forced-colors/native equivalents, 200% text/reflow, Touch Assistance, RTL/localization, TalkBack, Switch Access, Voice Access, adaptive form factors, Human Visual Excellence, and representative physical-device ergonomics. | High | Source mapping exists; `applicable-migration-required` / nonconformant until full acceptance evidence exists |
| FR-023 | Integrate and independently validate Privacy Shield, Wardveil Security, Everkeep, GoreeCloud Identity where applicable, GoreeCloud Mesh, and GoreeCloud Manager without treating source-local safeguards as accepted platform authority. | High | Blocked/acceptance incomplete for all listed platform systems |
| FR-024 | Treat Apple-platform keyboard support as product direction only until a separate native implementation, privacy model, packaging path, input-method constraints, platform integration, and acceptance evidence exist. | Low | Planned direction; no current implementation claim |
| FR-025 | Complete representative Android host/editor/OEM/version typing tests, accessibility and latency/performance/power acceptance, controlled production signing and key custody, upgrade/rollback/recovery validation, release provenance, Release Candidate approval, production acceptance, and Stable qualification against the exact release revision. | High | Planned release gate; current exact-head CI is Development evidence only |

## Sequencing and authority rules

1. Preserve typed/editor content minimization, sensitive-editor fail-closed behavior, and the zero-network current input path while local typing features advance.
2. Finish current cursor-control and utility/input-surface Development work before representing those experiences as complete; physical-device, RTL/BiDi, accessibility, latency, and multi-host acceptance remain separate gates.
3. Advance multilingual, gesture typing, adaptive layouts, emoji/discovery, and broader Quill assistance as independently testable tranches rather than placeholder chrome.
4. Do not implement or market Secure Paste as system-wide clipboard isolation until the privileged platform broker and Privacy Shield authorization path exist and are accepted. Clipboard history remains separately governed.
5. Keep portability schema expansion explicit and privacy-reviewed; do not make typed content, composing/surrounding-editor context, suggestions/learned input, emoji recents/frequency history, clipboard content/history, key history, sensitive-editor content, telemetry identifiers, credentials, or secrets portable by default.
6. Complete current GLAZE UI V1.3 application acceptance and independent Privacy Shield, Wardveil Security, Everkeep, Identity, Mesh, and Manager acceptance before Release Candidate or Stable claims.
7. Bind production promotion to exact-revision representative-device/host/editor/accessibility/performance evidence, controlled signing, recovery/rollback, release provenance, and explicit production approval.

## Reconciliation rule

At each material feature change, reconcile this roadmap against the authoritative project record, current repository state, applicable platform-system requirements, the Drive-side roadmap, and GoreeCloud Tasks Management. Missing obligations, stale status, duplicated work, roadmap drift, or undocumented disposition changes are defects to correct.
