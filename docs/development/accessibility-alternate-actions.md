# Accessible Long-Press Alternate Actions — Development

Status: **Development**

This continuation extends GoreeCloud Keyboard's merged native virtual-accessibility foundation so deterministic device-local key alternates can be discovered and activated without requiring a touch long-press gesture.

The intended Android accessibility contract is:

- a rendered key exposes ordinary click semantics through the existing virtual node;
- keys with entries in the existing `KeyAlternates` catalog additionally expose Android long-click semantics;
- accessibility long-click opens the same viewport-bounded alternate popup used by touch;
- each visible alternate is exposed as its own virtual accessibility button;
- choosing an alternate commits through the same `KeyboardView.Listener.onText` path used by touch alternate selection;
- impossible popup geometry fails closed rather than exposing invisible alternate controls.

The feature consumes only the already-rendered key label, key/popup geometry, the deterministic first-party alternate catalog, and an explicit accessibility action. It does not inspect editor text, query the clipboard, learn from input, persist alternate usage, request network data, or gain GoreeCloud Identity, Mesh, Privacy Shield, Wardveil Security, or other platform authority.

Representative TalkBack and Switch Access validation on physical devices remains required before accessibility or Stable acceptance can be claimed.
