# Keyboard accessibility state semantics

Status: **Development accessibility-hardening evidence**

## Purpose

GoreeCloud Keyboard renders its key grid and emoji category strip as custom-drawn controls and exposes them through `ExploreByTouchHelper` virtual accessibility nodes. Stateful controls must communicate their current state without relying only on visual selection.

## Implemented boundary

The native virtual-node delegate now exposes localized textual state descriptions for the stateful controls already represented by the rendered keyboard:

- Shift exposes `On` when shifted and `Off` when not shifted.
- The currently selected emoji category exposes `Selected` while retaining the existing Android selected-state semantic.

The implementation does not inspect editor contents, typed text, clipboard data, suggestion history, network data, or hidden input state. Existing click and alternate-character actions continue to route through the normal Keyboard listener path.

## Validation

Android instrumentation coverage exercises the actual platform accessibility-node provider and verifies Shift `Off`/`On` state descriptions and `Selected` state on the current emoji category.

Exact-head repository CI remains the source/runtime validation authority for this Development candidate.

## Authority and non-claims

This is native accessibility semantic hardening, not human assistive-technology acceptance. It does not establish TalkBack, Switch Access, Voice Access, Touch Assistance, large-text, RTL/localization, phone/tablet/foldable, physical-device, latency/performance, Privacy Shield, Wardveil Security, Release Candidate, or Stable acceptance.
