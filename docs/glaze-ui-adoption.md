# Glaze UI 1.6 Adoption Candidate — GoreeCloud Keyboard

Status: **Adoption Candidate**  
Required Stable baseline: **Glaze UI 1.6.0**  
Reviewed canonical Stable revision: `9dcd39dad0ade79fb01dfb1b6b39f6bf2c167471`  
Production eligible on the Glaze UI gate: **no**

## Scope

GoreeCloud Keyboard targets Glaze UI 1.6.0 as the current Stable design-system authority for keyboard geometry, spacing, interaction feedback, accessibility, visual state, and adaptive presentation. This record establishes current-Stable migration work; it does not claim complete Glaze UI conformance or production acceptance.

The current native Android surface remains a first-party `KeyboardView`. Existing key geometry, suggestion presentation, input semantics, and privacy behavior are retained while Glaze UI 1.6 mapping and acceptance are expanded incrementally.

Glaze UI 1.6 retains the Stable 1.5 visual and interaction foundations and promotes Evidence Presentation and Authority Surfaces plus Adaptive Workspace and Navigation. Keyboard does not claim those promoted systems complete merely by updating its Stable target. Adaptive Workspace requirements apply where Keyboard exposes applicable settings, panes, or window-responsive surfaces; Evidence Presentation applies only if Keyboard presents producer-authoritative GoreeCloud platform evidence.

## Repository-local evidence

- `KeyboardView` is the first-party Android rendering and pointer-input surface.
- The Android manifest requests no network permission.
- Unit tests cover the local suggestion and sensitive-editor boundaries.
- Android CI runs repository-local Glaze UI / Glaze Motion governance checks, unit tests, debug assembly, and a dedicated emulator runtime job.
- Experimental Glaze Motion evaluation remains quarantined to documentation, validation tooling, and Android test source.

## Acceptance boundary

This Adoption Candidate state does not establish full phone/tablet native or rendered Glaze UI acceptance, 1.6 Adaptive Workspace acceptance where applicable, TalkBack/switch-access acceptance, representative physical-device acceptance, production release acceptance, or full form-factor coverage. The emulator runtime evidence retained alongside this record is a bounded Android interaction/reduced-motion evaluation, not final product certification.

Glaze UI 1.6 Stable remains the production design-system authority. Experimental Glaze Motion is not a production dependency.
