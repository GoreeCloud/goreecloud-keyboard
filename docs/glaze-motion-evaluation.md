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

Keyboard now targets the current governed Stable **GLAZE UI V1.4 / `1.4.0`** design-system authority at merged source revision `84cb3db4884042f0fa25ed6d475a127fb110f596`. Motion remains separately Experimental and is not incorporated into Stable Glaze merely because the visual design-system target advanced.

The Keyboard V1.4 mapping is still application-level Development evidence. This Glaze Motion evaluation provides no V1.4 consumer-conformance, production, Stable, accessibility, or physical-device evidence and cannot promote the Motion subsystem.

Human/manual/physical-device/subjective optical validation assigned to Glaze UI V1.4.1 remains separate from this Motion evaluation.

This emulator evidence is not physical-device certification, full rendered acceptance, TalkBack or Switch Access acceptance, representative performance/power acceptance, or production Glaze Motion activation. It remains insufficient for promotion by itself and does not establish adoption of a later Glaze Motion revision.
