# Glaze Motion 0.5 Experimental Evaluation — GoreeCloud Keyboard

Lifecycle: **Experimental 0.5**  
Reviewed canonical revision: `b386c793c047e2f5d5d92125732f142e7fdf32dc`  
Runtime compatibility baseline: **0.4.0**  
Evaluation mode: **native Android interaction mapping, test-only**  
Production dependency: **no**

## Purpose

This historical evaluation maps Glaze Motion 0.5 Motion Core semantics to GoreeCloud Keyboard's real first-party Android `KeyboardView` interaction surface without importing or activating Experimental Motion in production source.

The bounded evaluation checks actual key and suggestion activation behavior under an Android emulator with platform animations disabled, then applies the 0.5 test-only semantic policy for press timing and optional settling.

## Evaluated semantics

- A real keyboard key remains semantically inactive on press-down and commits exactly once on release.
- Suggestion selection remains functional through the actual `KeyboardView` hit-testing and listener path.
- The test-only mapping uses the 0.5 `micro` duration of 90 ms, press scale 0.98, and maximum concurrent settling budget of 12.
- Platform-disabled animations collapse optional Motion settling to zero while semantic input remains available.
- The production source contains no `GlazeMotionExperimental` marker and no Experimental runtime dependency.

## Boundary

Keyboard targets the current **GLAZE UI V1.3 / `1.3.0` Stable** consumer line at exact Stable integration revision `fc7cc91d2eace8da2371371c2855c24cbcb326a1`. V1.3 Adaptive Resonance inherits the Frosted Neutral material foundation while adding bounded adaptive expression, ergonomic composition, and resilience requirements. The Keyboard mapping deliberately keeps typed/editor content outside adaptive-color authority.

This Glaze Motion document remains separate: Motion 0.5 is still Experimental/test-only and supplies no production Motion authority, no additional V1.3 conformance evidence, and no Release Candidate evidence. A Stable Glaze UI design-system target does not promote an independently Experimental Motion subsystem.

This emulator evidence is not physical-device certification, full rendered acceptance, TalkBack or Switch Access acceptance, representative performance/power acceptance, rollback acceptance, or production Glaze Motion activation. It remains insufficient for promotion by itself and does not establish adoption of a later Glaze Motion revision.
