# GoreeCloud Keyboard

GoreeCloud Keyboard is an original, native GoreeCloud keyboard implementation for Android and Apple platforms. It is being built from the ground up rather than from a third-party keyboard codebase.

## Current foundation

The first implementation slice establishes the native Android input-method foundation with:

- Android `InputMethodService` integration.
- A first-party keyboard rendering surface.
- Core QWERTY input, shift, backspace, space, and enter actions.
- A local suggestion boundary designed for future GoreeCloud Quill integration.
- Privacy-by-default behavior with no network permission.
- Explicit Glaze UI, Wardveil Security, Privacy Shield, and Everkeep integration boundaries.
- Continuous integration for the Android source tree.

## Product direction

The keyboard is intended to grow into a privacy-first input platform with swipe typing, local suggestions and correction, dictionaries, multilingual input, clipboard tools, voice/input adapters where appropriate, accessibility, one-handed and split layouts, tablet/foldable adaptation, and GoreeCloud Quill writing capabilities.

## Development model

This repository contains original GoreeCloud-owned application code. Third-party libraries may be used only as narrowly scoped supporting dependencies where justified; they must not become the primary keyboard implementation.

## Status

Development — native foundation.
