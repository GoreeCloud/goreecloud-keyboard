# GoreeCloud Keyboard — Feature Roadmap

**Status:** Active roadmap control  
**As of:** September 10, 2026  
**Authoritative project record:** `Project Specification — Keyboard`  
**Canonical repository:** `GoreeCloud/goreecloud-keyboard`  
**Drive control:** `GoreeCloud/Feature Roadmap/GoreeCloud Keyboard/FEATURE-ROADMAP.docx`

## Purpose

This file is the repository-side feature roadmap control for GoreeCloud Keyboard. It records current planned and recommended feature work without replacing the authoritative project record, implementation evidence, release gates, or GoreeCloud Tasks Management.

This roadmap and the Drive-side `FEATURE-ROADMAP.docx` must remain materially synchronized. No feature is Stable or complete solely because it appears here; lifecycle claims require applicable implementation, validation, review, release, and runtime evidence.

## Roadmap

| ID | Feature / obligation | Priority | Current state |
| --- | --- | --- | --- |
| FR-001 | Reconcile and maintain every current planned or recommended GoreeCloud Keyboard feature from the authoritative project record and verified repository evidence in this roadmap. | High | Ongoing control |
| FR-002 | Move actionable feature obligations into GoreeCloud Tasks Management when required, preserving priority, dependency, and lifecycle disposition. | High | Ongoing control |
| FR-003 | Do not mark features implemented, complete, cancelled, or superseded without authoritative evidence and synchronized repository/Drive roadmap updates. | High | Ongoing control |
| FR-004 | Deliver privacy-bounded spacebar cursor control with explicit device-local user control, ordinary Space tap behavior, bounded horizontal movement, fail-closed gesture handling, and no surrounding-text reconstruction. | High | Development implementation present on the active cursor-control stack; device-local enable/disable preference is under review. Physical-device ergonomics, RTL/BiDi behavior, accessibility acceptance, and release evidence remain open. |
| FR-005 | Continue local-first typing improvements without retaining typed content or silently expanding portable preference scope. | High | Ongoing development constraint |
| FR-006 | Validate current Stable Glaze UI, Wardveil, Privacy Shield, Everkeep, Mesh, Identity, and Manager contracts before promotion beyond Development. | High | Acceptance evidence incomplete; no Stable claim |

## Reconciliation rule

At each material feature change, reconcile this roadmap against the authoritative project record, current repository state, applicable platform-system requirements, the Drive-side roadmap, and GoreeCloud Tasks Management. Missing obligations, stale status, duplicated work, roadmap drift, or undocumented disposition changes are defects to correct.
