# Glaze UI 1.5 Adoption Candidate — GoreeCloud Keyboard

Status: **Adoption Candidate**  
Required Stable baseline: **Glaze UI 1.5.0**  
Reviewed canonical Stable revision: `2e1618397f6ebcdd254a76bfdd7e98846f2c5aa3`  
Production eligible on the Glaze UI gate: **no**

## Scope

GoreeCloud Keyboard targets Glaze UI 1.5.0 as the current Stable design-system authority for keyboard geometry, spacing, interaction feedback, accessibility, visual state, and adaptive presentation. This record establishes current-Stable adoption work; it does not claim complete Glaze UI conformance or production acceptance.

The current native Android surface remains a first-party `KeyboardView`. Existing key geometry, suggestion presentation, input semantics, and privacy behavior are retained while Glaze UI 1.5 mapping and acceptance are expanded incrementally.

## Repository-local evidence

- `KeyboardView` is the first-party Android rendering and pointer-input surface.
- The Android manifest requests no network permission.
- Unit tests cover the local suggestion and sensitive-editor boundaries.
- Android CI runs repository-local Glaze UI / Glaze Motion governance checks, unit tests, debug assembly, and a dedicated emulator runtime job.
- Experimental Glaze Motion evaluation remains quarantined to documentation, validation tooling, and Android test source.

## Acceptance boundary

This Adoption Candidate state does not establish full phone/tablet native or rendered Glaze UI acceptance, TalkBack/switch-access acceptance, representative physical-device acceptance, production release acceptance, or full form-factor coverage. The emulator runtime evidence added alongside this record is a bounded Android interaction/reduced-motion evaluation, not final product certification.

Glaze UI 1.5 Stable remains the production design-system authority. Experimental Glaze Motion is not a production dependency.
